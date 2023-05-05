package mzc.app.view.components.settings;

import javafx.scene.Node;
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
        root.setPrefWidth(1000);
        root.setPrefHeight(1000);
        getViewModel().getLeftBox().getChildren().add(getViewModel().getLeftTitle());
        root.add(getViewModel().getLeftBox(), 0, 0);
        if (isRightUsed) {
            getViewModel().getRightBox().getChildren().add(getViewModel().getRightTitle());
            root.add(getViewModel().getRightBox(), 1, 0);
        }
        return root;
    }
}
