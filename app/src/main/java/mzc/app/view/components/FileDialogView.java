package mzc.app.view.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.FileDialogViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.function.Consumer;

@ModelInject(FileDialogViewModel.class)
public class FileDialogView extends BaseView<FileDialogViewModel> {
    public FileDialogView(@NotNull Button triggerButton, @NotNull Consumer<File> onFileSelected) {
        getViewModel().setButton(triggerButton);
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
