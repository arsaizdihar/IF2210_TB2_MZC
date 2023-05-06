package mzc.app.view_model.components.product_list;

import mzc.app.utils.reactive.Context;
import mzc.app.view.components.member_list.HistoryTransactionView;
import mzc.app.view.components.product_list.AddProductView;
import mzc.app.view.components.product_list.EditProductView;
import mzc.app.view.components.product_list.ProductDisplayView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;
import mzc.app.view_model.page.SplitPageViewModel;

public class LeftSideProductViewModel extends LeftSideViewModel {
    @Override
    public void init() {
        super.init();
        setOnButtonClicked((e) -> {
//            getChildren().getValue().add(createView(new TestView()));
            setRightSide();
            getChildren().forceUpdate();
        });
        var products = getAdapter().getProduct().getAll();

        var productViews = products.stream().map(product -> {
           var productView = new ProductDisplayView(product);
            productView.getViewModel().getEditButton().setOnAction((e) -> {
                EditProductView editProductView = new EditProductView(product);
                Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
                context.getValue().setRight(editProductView);
                getChildren().forceUpdate();
            });
           createView(productView);
           return productView;
        });
//        products.forEach(e -> {
//            getChildren().getValue().add(createView(new ProductDisplayView(e)));
//        });
        getChildren().getValue().addAll(productViews.toList());
        getChildren().forceUpdate();


    }

    private void setRightSide() {
        AddProductView right = new AddProductView();
        Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
        context.getValue().setRight(right);
    }

    public void reload() {

    }
}
