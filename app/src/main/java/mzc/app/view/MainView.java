package mzc.app.view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.MainViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(MainViewModel.class)
@Getter
public class MainView extends BaseView<MainViewModel> {

    public MainView() {
        super();
    }

    @Override
    public @NotNull Parent getView() {
        return getViewModel().getMenuView().getView();
    }
}
