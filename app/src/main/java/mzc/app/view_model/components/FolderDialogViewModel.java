package mzc.app.view_model.components;

import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

public class FolderDialogViewModel extends BaseViewModel {
    @Getter
    private final @NotNull DirectoryChooser directoryChooser = new DirectoryChooser();
    @Getter @Setter
    private Button button;
    public void setTitle(String title) {
        directoryChooser.setTitle(title);
    }
}
