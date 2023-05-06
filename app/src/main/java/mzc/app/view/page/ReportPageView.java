package mzc.app.view.page;

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

        var headerBox = getViewModel().getHeaderBox();
        root.getChildren().add(headerBox);

        var spaser = getViewModel().getSpaser();
        headerBox.getChildren().add(spaser);
        HBox.setHgrow(spaser, javafx.scene.layout.Priority.ALWAYS);

        var title = getViewModel().getTitleLabel();
        spaser.getChildren().add(title);

        var printButton = getViewModel().getPrintButton();
        headerBox.getChildren().add(printButton);

        var listView = getViewModel().getListView();
        root.getChildren().add(listView);



        return getViewModel().getRoot();
    }
}
