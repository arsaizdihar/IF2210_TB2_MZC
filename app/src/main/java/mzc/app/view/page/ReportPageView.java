package mzc.app.view.page;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.app.view_model.page.ReportPageViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ReportPageViewModel.class)
public class ReportPageView extends PageView<ReportPageViewModel> {

    @Override
    public @NotNull Node getView() {
        var root = getViewModel().getRoot();
        root.setAlignment(Pos.TOP_CENTER);

        var padder = getViewModel().getPadder();
        root.getChildren().add(padder);
        padder.setPadding(new Insets(0, 30, 5, 20));

        var title = getViewModel().getTitleLabel();
        title.getStyleClass().add("history-title");
        padder.setLeft(title);

        getViewModel().createPrintButton();
        getViewModel().getPrintButton().getStyleClass().add("btn");
        padder.setRight(getViewModel().getPrintButton());

        var scrollPane = getViewModel().getScrollPane();
        root.getChildren().add(scrollPane);
        scrollPane.setContent(getViewModel().getContainer());

        getViewModel().getContainer().setPadding(new Insets(0, 30, 0, 20));


        getViewModel().iterateFixedBill();

        getViewModel().getScrollPane().setFitToWidth(true);
        getViewModel().getContainer().setFillWidth(true);

        return getViewModel().getRoot();
    }
}
