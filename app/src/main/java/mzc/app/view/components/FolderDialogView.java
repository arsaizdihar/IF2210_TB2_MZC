package mzc.app.view.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.FolderDialogViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.function.Consumer;

@ModelInject(FolderDialogViewModel.class)
public class FolderDialogView extends BaseView<FolderDialogViewModel> {
    public FolderDialogView(@NotNull Button triggerButton, @NotNull Consumer<File> onFileSelected) {
        getViewModel().setButton(triggerButton);
        triggerButton.setOnAction((e) -> {
            File file = getViewModel().getDirectoryChooser().showDialog(triggerButton.getScene().getWindow());
            onFileSelected.accept(file);
        });
    }
    @Override
    public @NotNull Node getView() {
        return getViewModel().getButton();
    }
}
