package mzc.app.view_model.components.cashier;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.NonNull;
import mzc.app.model.CustomerType;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import mzc.app.modules.pricing.PriceCalculator;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.pipelines.IPricePipeline;
import mzc.app.modules.pricing.pipelines.PointPipeline;
import mzc.app.modules.pricing.pipelines.VIPDiscountPipeline;
import mzc.app.modules.pricing.price.ItemListPrice;
import mzc.app.modules.pricing.price.ItemPrice;
import mzc.app.utils.reactive.State;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.page.CashierPageViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubtotalViewModel extends BaseViewModel {
    @Getter
    protected State<Boolean> usePoints = new State<>(false);

    @Getter
    protected State<List<PricePipelineResult>> pipelineResults = new State<>(new ArrayList<>());

    @Getter
    protected VBox pipelineContainer = new VBox();

    @Getter
    protected VBox actionsContainer = new VBox();

    @Getter
    protected CheckBox usePointsCheckbox = new CheckBox();

    @Getter
    protected Button checkoutButton = new Button();

    @Override
    public void init() {
        super.init();

        var context = useContext(PaymentSummaryViewModel.PaymentSummaryContext.class).getValue();

        context.getProductItems().addListener((observable, prev, productItems) -> {
            this.calculate(productItems.values().stream().toList());

            this.checkoutButton.setDisable(productItems.isEmpty());
        });

        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();
        cashierContext.getBill().addListener((observableValue, prev, next) -> {
            this.usePointsCheckbox.setDisable(next.getCustomer().getType() != CustomerType.MEMBER && next.getCustomer().getType() != CustomerType.VIP);
        });

        this.usePoints.bindBidirectional(this.usePointsCheckbox.selectedProperty());

        var customerType = cashierContext.getBill().getValue().getCustomer().getType();

        this.usePointsCheckbox.setDisable(customerType != CustomerType.MEMBER && customerType != CustomerType.VIP);

        this.usePoints.addListener(((observableValue, aBoolean, t1) -> {
            this.calculate(context.getProductItems().getValue().values().stream().toList());
        }));

        this.checkoutButton.setOnAction(e -> {
            this.checkout();
        });

        this.checkoutButton.setDisable(true);
    }

    protected List<IPricePipeline> getPipelines() {
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var customer = context.getBill().getValue().getCustomer();

        List<IPricePipeline> pipelines = new ArrayList<>();

        if (!this.usePointsCheckbox.isDisabled() && this.usePoints.getValue()) {
            if (customer.getType().equals(CustomerType.BASIC)) {
                throw new RuntimeException("Basic member are not allowed to use points");
            }

            pipelines.add(new PointPipeline(customer.getPoints()));
        }

        if (customer.getType().equals(CustomerType.VIP)) {
            pipelines.add(new VIPDiscountPipeline());
        }

        return pipelines;
    }

    protected void calculate(@NonNull List<ItemPrice> productItems) {
        var calculator = new PriceCalculator(this.getPipelines());
        this.pipelineResults.setValue(calculator.calculate(new ItemListPrice(productItems)));

        this.pipelineContainer.getChildren().clear();

        this.getPipelineResults().getValue().forEach(result -> {
            this.pipelineContainer.getChildren().add(new Label(result.getName() + " " + result.getNominal()));
        });

        var lastResult = this.pipelineResults.getValue().get(this.pipelineResults.getValue().size() - 1);

        this.pipelineContainer.getChildren().add(new Label("Total " + PriceFactory.createPriceView(lastResult.getTotal())));
    }

    protected void checkout() {
        var validation = validateOrder();
        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var oldBill = cashierContext.getBill().getValue();

        if (validation.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Checkout Failed");
            alert.setHeaderText(null);
            alert.setContentText(String.join("\n", validation));
            alert.showAndWait();

            cashierContext.loadBill(oldBill.getCustomer());
        } else {
            placeOrder();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Checkout Success");
            alert.setHeaderText(null);
            alert.setContentText("Pembelian berhasil");
            alert.showAndWait();

            if (oldBill.getCustomer().getType() != CustomerType.BASIC) {
                cashierContext.loadBill(oldBill.getCustomer());
            } else {
                cashierContext.loadBill();
            }
        }
    }

    protected List<String> validateOrder() {
        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();

        var productBills = getAdapter().getBill().getProducts(cashierContext.getBill().getValue());

        List<String> errors = new ArrayList<>();

        if (productBills.size() == 0) {
            errors.add("Tidak ada item");
            return errors;
        }

        AtomicBoolean outOfStock = new AtomicBoolean(false);
        AtomicBoolean productDeleted = new AtomicBoolean(false);

        // check for out of stock or deleted products
        productBills.forEach(productBill -> {
            var product = productBill.getProduct();

            if (productBill.getAmount() > productBill.getProduct().getStock()) {
                outOfStock.set(true);
            }

            if (product.getDeleted()) {
                productDeleted.set(true);
                // delete productBill for deleted product
                getAdapter().getProductBill().delete(productBill);
            }
        });

        if (outOfStock.get()) {
            errors.add("Product out of stock");
        }

        if (productDeleted.get()) {
            errors.add("Product Deleted");
        }

        return errors;
    }

    protected void placeOrder() {
        // assume validated
        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var productBills = getAdapter().getBill().getProducts(cashierContext.getBill().getValue());

        var productItems = new ArrayList<ItemPrice>();

        productBills.forEach(productBill -> {
            if (productBill.getAmount() > 0) {
                ItemPrice itemPrice = new ItemPrice(productBill.getAmount(), PriceFactory.createPriceView(productBill.getProduct().getPrice()));
                productItems.add(itemPrice);
            }
        });

        var customer = cashierContext.getBill().getValue().getCustomer();

        if (customer == null) {
            throw new RuntimeException("Customer should not be null");
        }

        // create fixed bills
        FixedBill fixedBill = new FixedBill(customer);
        getAdapter().getFixedBill().persist(fixedBill);

        // create product history and subtract product stock
        productBills.forEach(productBill -> {
            var item = new ProductHistory(productBill.getProduct(), fixedBill, productBill.getAmount());
            getAdapter().getProductHistory().persist(item);

            var product = productBill.getProduct();
            product.setStock(product.getStock() - productBill.getAmount());
        });

        // calculate pipelines
        var calculator = new PriceCalculator(this.getPipelines());
        var pipelineResult = calculator.calculate(new ItemListPrice(productItems));

        BigDecimal point = pipelineResult.get(pipelineResult.size() - 1).getTotal().getValue().divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN).setScale(0, RoundingMode.HALF_UP);

        pipelineResult.forEach(pricePipelineResult -> {
            if (!pricePipelineResult.getName().equals("Subtotal")) {
                var pipeline = new ProductHistory(pricePipelineResult.getName(),
                        pricePipelineResult.getNominal().getValue(),
                        new BigDecimal(0),
                        pricePipelineResult.getName(),
                        "",
                        1,
                        fixedBill);

                getAdapter().getProductHistory().persist(pipeline);

                if (pricePipelineResult.getName().equals("Poin")) {
                    customer.setPoints(0);
                    getAdapter().getCustomer().persist(customer);
                }
            }
        });

        // add point
        if (customer.getType() != CustomerType.BASIC) {
            customer.setPoints(point.intValue() + customer.getPoints());
            getAdapter().getCustomer().persist(customer);
        }

        // delete product bill
        productBills.forEach(productBill -> {
            getAdapter().getProductBill().delete(productBill);
        });

        // delete bill
        // todo sepertinya tidak perlu. Bisa direuse
    }
}
