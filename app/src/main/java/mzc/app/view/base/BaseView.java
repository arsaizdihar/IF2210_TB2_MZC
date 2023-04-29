package mzc.app.view.base;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import mzc.app.utils.ViewLoader;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public abstract class BaseView<T extends BaseViewModel> {
    private @NotNull T viewModel;

    public BaseView() {
        ViewLoader.load(this);
    }

    abstract public @NotNull Node getView();

}
