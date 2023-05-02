package mzc.app.view.components.product_list;

import javafx.scene.Node;
import javafx.scene.control.Label;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.product_list.TestViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(TestViewModel.class)
public class TestView extends BaseView<TestViewModel> {
    @Override
    public @NotNull Node getView() {
        return new Label("test");
    }
}
