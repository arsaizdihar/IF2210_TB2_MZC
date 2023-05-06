package mzc.app.view.components.settings;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.settings.SettingsTabViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SettingsTabViewModel.class)
public abstract class SettingsTabView<T extends SettingsTabViewModel> extends BaseView<T> {

    @NotNull
    private Boolean isRightUsed = false;
    public SettingsTabView(String leftTitle, String  rightTitle) {
        super();
        getViewModel().getLeftTitle().setText(leftTitle);
        getViewModel().getRightTitle().setText(rightTitle);
        isRightUsed = true;
    }

    public SettingsTabView(String leftTitle) {
        super();
        getViewModel().getLeftTitle().setText(leftTitle);
    }

    @Override
    public @NotNull Node getView() {
        var root = getViewModel().getRoot();
        root.getStyleClass().add("settings-tab");
        getViewModel().getSaveButtonL().getStyleClass().addAll("btn", "btn-success");
        getViewModel().getLeftBox().getChildren().add(getViewModel().getSettingsBoxL());
        getViewModel().getSettingsBoxL().getStyleClass().add("settings");
        getViewModel().getSettingsBoxL().getChildren().add(getViewModel().getLeftTitle());
        var btnContainer = new HBox();
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(getViewModel().getSaveButtonL());
        getViewModel().getLeftBox().getChildren().add(btnContainer);
        VBox.setVgrow(getViewModel().getSettingsBoxL(), Priority.ALWAYS);
        getViewModel().getLeftBox().getStyleClass().addAll("settings-left", "settings-side");
        root.add(getViewModel().getLeftBox(), 0, 0);
        if (isRightUsed) {
            getViewModel().getSaveButtonR().getStyleClass().addAll("btn", "btn-success");
            getViewModel().getRightBox().getChildren().add(getViewModel().getSettingsBoxR());
            getViewModel().getSettingsBoxR().getStyleClass().add("settings");
            getViewModel().getSettingsBoxR().getChildren().add(getViewModel().getRightTitle());
            btnContainer = new HBox();
            btnContainer.setAlignment(Pos.CENTER);
            btnContainer.getChildren().add(getViewModel().getSaveButtonR());
            getViewModel().getRightBox().getChildren().add(btnContainer);
            VBox.setVgrow(getViewModel().getSettingsBoxR(), Priority.ALWAYS);
            getViewModel().getRightBox().getStyleClass().addAll("settings-right", "settings-side");
            root.add(getViewModel().getRightBox(), 1, 0);
        }
        return root;
    }
}
