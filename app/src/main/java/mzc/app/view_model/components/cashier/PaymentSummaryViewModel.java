package mzc.app.view_model.components.cashier;

import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.model.ProductBill;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.price.ItemPrice;
import mzc.app.utils.Tuple;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.cashier.CustomerSelectorView;
import mzc.app.view.components.cashier.ItemsView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import mzc.app.view_model.page.CashierPageViewModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentSummaryViewModel extends RightSideViewModel {
    public static class PaymentSummaryContext {
        @Getter
        private State<List<ProductBill>> products = new State<>(new ArrayList<>());

        @Getter
        private State<List<Tuple<ProductBill, ItemPrice>>> productItems = new State<>(new ArrayList<>());
    }

    @Getter
    private CustomerSelectorView customerSelector = new CustomerSelectorView();

    @Getter
    private ItemsView itemsView = new ItemsView();

    @Getter
    private VBox container = new VBox();


    @Override
    public void init() {
        super.init();

        var paymentContext = new Context<>(new PaymentSummaryContext());
        addContext(paymentContext);

        paymentContext.getValue().getProducts().addListener((observableValue, prev, productBills) -> {
            paymentContext.getValue().getProductItems().getValue().clear();

            System.out.println("Got product biils with size of " + productBills.size());

            productBills.forEach(productBill -> {
                System.out.println(productBill.getProductId() + " with amount " + productBill.getAmount());
                if (productBill.getAmount() > 0) {
                    ItemPrice itemPrice = new ItemPrice(productBill.getAmount(), PriceFactory.createPriceView(productBill.getProduct().getPrice()));
                    paymentContext.getValue().getProductItems().getValue().add(new Tuple<>(productBill, itemPrice));
                }
            });

            System.out.println("Listening to products (Product Bill) update. Updateing product items with size of " + paymentContext.getValue().getProductItems().getValue().size());

            paymentContext.getValue().getProductItems().forceUpdate();
        });

        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();

        cashierContext.getShouldUpdateSummary().addListener(((observableValue, prev, next) -> {
            System.out.println("Listening to should update. got value " + next);
            if (next) {
                reloadSummary();
            }
        }));

        createView(this.customerSelector);
        createView(this.itemsView);
        reloadSummary();
    }

    public void reloadSummary() {
        var paymentContext = useContext(PaymentSummaryContext.class).getValue();
        paymentContext.getProducts().getValue().clear();
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var products = getAdapter().getBill().getProducts(context.getBill().getValue());
        paymentContext.getProducts().getValue().addAll(products);
        System.out.println("Reloading product summary (product bill) got size " + products.size());
        paymentContext.getProducts().forceUpdate();

        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();
        cashierContext.getShouldUpdateSummary().setValue(false);
    }
}
