package mzc.app.view_model.components.product_list;

import mzc.app.view.components.product_list.TestView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;

public class LeftSideProductViewModel extends LeftSideViewModel {
    @Override
    public void init() {
        super.init();
        setOnButtonClicked((e) -> {
            getChildren().getValue().add(createView(new TestView()));
            getChildren().forceUpdate();
        });
    }
}
