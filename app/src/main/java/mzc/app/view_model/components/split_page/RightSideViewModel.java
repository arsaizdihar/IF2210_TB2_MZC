package mzc.app.view_model.components.split_page;

import javafx.scene.control.Label;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

public class RightSideViewModel extends BaseViewModel {
    @Getter
    private final @NotNull Label emptyLabel = new Label("Test");

    public RightSideViewModel() {
    }
}
