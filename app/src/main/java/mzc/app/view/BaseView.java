package mzc.app.view;

import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;
import mzc.app.utils.ViewLoader;
import mzc.app.view_model.BaseViewModel;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public abstract class BaseView<T extends BaseViewModel> {
    private @NotNull T viewModel;

    public BaseView() {
        ViewLoader.load(this);
    }

    abstract public @NotNull Parent getView();
}
