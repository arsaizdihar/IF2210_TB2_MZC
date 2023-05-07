package mzc.app.view.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.FileDialogViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@ModelInject(FileDialogViewModel.class)
public class FileDialogView extends BaseView<FileDialogViewModel> {
    public  FileDialogView(@NotNull Button triggerButton, @NotNull Consumer<File> onFileSelected, String extType, String ext) {
        getViewModel().setButton(triggerButton);
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("All Files", ext);
        getViewModel().getFileChooser().getExtensionFilters().add(fileExtensions);
        getViewModel().getFileChooser().setInitialDirectory(new File(System.getProperty("user.dir")));
        triggerButton.setOnAction((e) -> {
            File file = getViewModel().getFileChooser().showOpenDialog(triggerButton.getScene().getWindow());
            if (file != null) {
                onFileSelected.accept(file);
            }
        });
    }
    public  FileDialogView(@NotNull Button triggerButton, @NotNull Consumer<File> onFileSelected, String extType, List<String> ext) {
        getViewModel().setButton(triggerButton);
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter(extType, ext);
        getViewModel().getFileChooser().getExtensionFilters().add(fileExtensions);
        getViewModel().getFileChooser().setInitialDirectory(new File(System.getProperty("user.dir")));
        triggerButton.setOnAction((e) -> {
            File file = getViewModel().getFileChooser().showOpenDialog(triggerButton.getScene().getWindow());
            if (file != null) {
                onFileSelected.accept(file);
            }
        });
    }
    @Override
    public @NotNull Node getView() {
        return getViewModel().getButton();
    }
}
