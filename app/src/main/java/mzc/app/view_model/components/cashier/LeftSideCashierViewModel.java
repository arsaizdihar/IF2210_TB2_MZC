package mzc.app.view_model.components.cashier;

import mzc.app.model.ProductBill;
import mzc.app.view.components.cashier.ProductView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;
import mzc.app.view_model.page.CashierPageViewModel;

public class LeftSideCashierViewModel extends LeftSideViewModel {
    @Override
    public void init() {
        super.init();
        this.reload();

        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        context.getBill().addListener(((observableValue, old, next) -> {
            this.reload();
        }));
    }

    public void reload() {
        getListView().getItems().clear();
        
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var products = getAdapter().getProduct().getAll();
        var productBills = context.getBill().getValue().getProducts();

        var views = products.stream().map(product -> {
            var productBill = productBills.stream().filter(pb -> pb.getProductId() == product.getId()).findFirst();

            if (productBill.isPresent()) {
                var view = new ProductView(productBill.get());
                createView(view);
                return view.getView();
            }

            var newProductBill = new ProductBill();
            newProductBill.setProduct(product);
            newProductBill.setBill(context.getBill().getValue());
            var view = new ProductView(newProductBill);
            createView(view);
            return view.getView();
        });

        getListView().getItems().addAll(views.toList());
    }
}
