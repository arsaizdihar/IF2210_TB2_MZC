package mzc.app.view_model.components;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

public class FileDialogViewModel extends BaseViewModel {
    @Getter
    private final @NotNull FileChooser fileChooser = new FileChooser();

    @Getter @Setter
    private Button button;

    public void setTitle(String title) {
        fileChooser.setTitle(title);
    }
}
