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
        triggerButton.getStyleClass().addAll("btn", "btn-primary");
        getViewModel().setButton(triggerButton);
        getViewModel().getDirectoryChooser().setInitialDirectory(new File(System.getProperty("user.dir")));
        triggerButton.setOnAction((e) -> {
            File file = getViewModel().getDirectoryChooser().showDialog(triggerButton.getScene().getWindow());
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
