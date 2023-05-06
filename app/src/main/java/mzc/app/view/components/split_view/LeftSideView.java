package mzc.app.view.components.split_view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mzc.app.utils.reactive.State;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.split_page.LeftSideViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LeftSideView<T extends LeftSideViewModel> extends BaseView<T> {

    public LeftSideView(String title, String buttonText) {
        getViewModel().setButtonText(buttonText);
        getViewModel().getTitleLabel().setText(title);
        init();
    }

    public LeftSideView(String title) {
        getViewModel().getTitleLabel().setText(title);
        init();
    }

    private void init() {
        var hBox = getViewModel().getHBox();
        hBox.setPadding(new Insets(0, 0, 20, 0));
        Pane spacer = new Pane();
        hBox.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.getChildren().add(getViewModel().getTitleLabel());
        getViewModel().getTitleLabel().getStyleClass().add("title");
        if (getViewModel().getButton() != null) {
            getViewModel().getButton().getStyleClass().addAll("btn", "btn-primary");
            hBox.getChildren().add(getViewModel().getButton());
        }
        var vbox = getViewModel().getVBox();
        vbox.getChildren().addAll(hBox);
        vbox.setPadding(new Insets(20));
        vbox.getStyleClass().add("left-side");
        vbox.getChildren().add(getViewModel().getListView());
        VBox.setVgrow(getViewModel().getListView(), Priority.ALWAYS);
    }


    @Override
    public @NotNull Node getView() {
        return getViewModel().getVBox();
    }
}
