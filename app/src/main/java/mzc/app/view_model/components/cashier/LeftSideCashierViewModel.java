package mzc.app.view_model.components.cashier;

import javafx.scene.control.TextField;
import lombok.Getter;
import mzc.app.model.Bill;
import mzc.app.model.Product;
import mzc.app.model.ProductBill;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.cashier.ProductView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;
import mzc.app.view_model.page.CashierPageViewModel;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LeftSideCashierViewModel extends LeftSideViewModel {

    @Getter
    private TextField searchBox = new TextField();

    @Getter
    private State<String> searchQuery = new State<>("");

    private Map<Long, ProductBill> productBillMap = null;

    private Set<Product> products = null;

    @Override
    public void init() {
        super.init();
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();

        searchQuery.bindBidirectional(searchBox.textProperty());

        searchQuery.addListener((observableValue, s, t1) -> {
            this.reload();
        });

        context.getBill().addListener(((observableValue, old, next) -> {
            this.reloadProductBill(next);
        }));

        this.reloadProductBill(context.getBill().getValue());
    }

    public void reloadProductBill(Bill bill) {
        this.products = getAdapter().getProduct().getAll();
        this.productBillMap = getAdapter().getBill().getProducts(bill).stream().collect(Collectors.toMap(ProductBill::getProductId, pb -> pb));
        this.searchQuery.forceUpdate();
    }

    public void reload() {
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();

        getChildren().getValue().clear();

        var views = products.stream().filter(product -> {
                    return searchQuery.getValue().equals("") ||
                            product.getName().toLowerCase().contains(searchQuery.getValue().toLowerCase()) ||
                            product.getCategory().toLowerCase().contains(searchQuery.getValue().toLowerCase());
                }
        ).map(product -> {
            var productBill = this.productBillMap.get(product.getId());

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
