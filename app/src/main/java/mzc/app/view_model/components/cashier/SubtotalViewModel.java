package mzc.app.view_model.components.cashier;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.NonNull;
import mzc.app.model.CustomerType;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import mzc.app.modules.pricing.PriceCalculator;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.PricePipelineResult;
import mzc.app.modules.pricing.pipelines.PointPipeline;
import mzc.app.modules.pricing.pipelines.PricePipeline;
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
    protected VBox pipelineNameContainer = new VBox();

    @Getter
    protected VBox pipelineValueContainer = new VBox();

    @Getter
    protected VBox actionsContainer = new VBox();

    @Getter
    protected CheckBox usePointsCheckbox = new CheckBox();

    @Getter
    protected Button checkoutButton = new Button();

    @Getter
    protected final VBox container = new VBox();

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

        container.setMinHeight(200);

        HBox info = new HBox();
        info.setPadding(new Insets(10, 30, 20, 10));
        info.getChildren().add(getActionsContainer());
        var spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        info.getChildren().add(spacer);
        info.getChildren().add(pipelineNameContainer);
        info.getChildren().add(pipelineValueContainer);

        pipelineNameContainer.getStyleClass().add("h5");
        pipelineValueContainer.getStyleClass().add("h5");

        getUsePointsCheckbox().setText("Gunakan poin");
        getCheckoutButton().setText("Checkout");
        getActionsContainer().getChildren().add(getUsePointsCheckbox());

        actionsContainer.setMinWidth(275);
        getUsePointsCheckbox().getStyleClass().add("h5");


        var actionTitle = new Text("Opsi Tambahan");
        actionTitle.getStyleClass().add("h4");
        actionTitle.setStyle("-fx-font-weight: bold");
        container.getChildren().add(0, actionTitle);

        container.getChildren().add(info);

        VBox.setVgrow(spacer, Priority.ALWAYS);
        container.getChildren().add(spacer);

        getCheckoutButton().getStyleClass().add("btn");
        getCheckoutButton().getStyleClass().add("btn-primary");

        var checkoutBox = new VBox(getCheckoutButton());
        checkoutBox.setAlignment(Pos.CENTER);
        checkoutBox.setPadding(new Insets(10, 0, 10, 0));
        container.getChildren().add(checkoutBox);

        cashierContext.getBill().addListener((observableValue, old, next) -> {
            if (next.getCustomer().getType() == CustomerType.BASIC) {
                getUsePointsCheckbox().setText("Gunakan poin");
            } else {
                getUsePointsCheckbox().setText("Gunakan poin (tersisa " + next.getCustomer().getPoints() + ")");
            }
        });
    }

    protected List<PricePipeline> getPipelines() {
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var customer = context.getBill().getValue().getCustomer();

        List<PricePipeline> pipelines = new ArrayList<>();

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

        this.pipelineNameContainer.getChildren().clear();
        this.pipelineValueContainer.getChildren().clear();

        this.getPipelineResults().getValue().forEach(result -> {
            this.pipelineNameContainer.getChildren().add(new Text(result.getName()));
            var text = "";

            if (result.getNominal().toString().startsWith("-")) {
                text = " " + result.getNominal();
            } else {
                text = "  " + result.getNominal();
            }

            this.pipelineValueContainer.getChildren().add(new Text(text));
        });

        var lastResult = this.pipelineResults.getValue().get(this.pipelineResults.getValue().size() - 1);
        this.pipelineNameContainer.getChildren().add(new Text("Total"));
        this.pipelineValueContainer.getChildren().add(new Text("  " + PriceFactory.createPriceView(lastResult.getTotal())));
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

            cashierContext.loadBill(oldBill.getCustomer());

            alert.showAndWait();
        } else {
            placeOrder();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Checkout Success");
            alert.setHeaderText(null);
            alert.setContentText("Pembelian berhasil");

            if (oldBill.getCustomer().getType() != CustomerType.BASIC) {
                cashierContext.loadBill(oldBill.getCustomer());
            } else {
                cashierContext.loadBill();
            }

            alert.showAndWait();
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
            getAdapter().getProduct().persist(product);
        });

        // calculate pipelines
        var pipelines = this.getPipelines();
        var calculator = new PriceCalculator(pipelines);
        var pipelineResult = calculator.calculate(new ItemListPrice(productItems));

        BigDecimal point = pipelineResult.get(pipelineResult.size() - 1).getTotal().getValue().divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN).setScale(0, RoundingMode.HALF_UP);

        pipelineResult.forEach(pricePipelineResult -> {
            if (!pricePipelineResult.getName().equals("Subtotal")) {
                if (pricePipelineResult.getName().equals("Poin")) {
                    customer.setPoints(0);
                    getAdapter().getCustomer().persist(customer);
                }
            }
        });

        pipelines.forEach(pipeline -> {
            var history = pipeline.createHistory();
            history.setBill(fixedBill);
            getAdapter().getProductHistory().persist(history);
        });

        // add point
        if (customer.getType() != CustomerType.BASIC) {
            customer.setPoints(point.intValue() + customer.getPoints());
            getAdapter().getCustomer().persist(customer);
        }

        // delete product bill
        productBills.stream().toList().forEach(productBill -> {
            getAdapter().getProductBill().delete(productBill);
        });

        // delete bill
        // todo sepertinya tidak perlu. Bisa direuse
    }
}
