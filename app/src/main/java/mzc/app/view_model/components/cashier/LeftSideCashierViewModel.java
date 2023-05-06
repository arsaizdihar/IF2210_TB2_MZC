package mzc.app.view_model.components.cashier;

import mzc.app.model.Bill;
import mzc.app.model.ProductBill;
import mzc.app.view.components.cashier.ProductView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;
import mzc.app.view_model.page.CashierPageViewModel;

import java.util.stream.Collectors;

public class LeftSideCashierViewModel extends LeftSideViewModel {
    @Override
    public void init() {
        super.init();
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        this.reload(context.getBill().getValue());

        context.getBill().addListener(((observableValue, old, next) -> {
            this.reload(next);
        }));
    }

    public void reload(Bill bill) {

        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var products = getAdapter().getProduct().getAll();
        var productBills = getAdapter().getBill().getProducts(bill).stream().collect(Collectors.toMap(ProductBill::getProductId, pb -> pb));

        getChildren().getValue().clear();

        var views = products.stream().map(product -> {
            var productBill = productBills.get(product.getId());

            if (productBill != null) {
                var view = new ProductView(productBill);
                createView(view);
                return view;
            }

            var newProductBill = new ProductBill();
            newProductBill.setProduct(product);
            newProductBill.setBill(context.getBill().getValue());
            var view = new ProductView(newProductBill);
            createView(view);
            return view;
        });

        getChildren().getValue().addAll(views.toList());
        getChildren().forceUpdate();
    }
}
