package mzc.app.view_model.components.product_list;

import mzc.app.utils.reactive.Context;
import mzc.app.view.components.product_list.AddProductView;
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
    }

    private void setRightSide() {
        AddProductView right = new AddProductView();
        Context<SplitPageViewModel.SplitPageContext> context = useContext(SplitPageViewModel.SplitPageContext.class);
        context.getValue().setRight(right);
    }
}
