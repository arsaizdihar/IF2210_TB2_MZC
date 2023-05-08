package mzc.app.view.components.member_list;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import mzc.app.annotation.ModelInject;
import mzc.app.model.Customer;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.member_list.HistoryTransactionViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(HistoryTransactionViewModel.class)
public class HistoryTransactionView extends RightSideView<HistoryTransactionViewModel> {

    public HistoryTransactionView(Customer customer) {
        super();
        getViewModel().setCustomer(customer);
        init();
    }

    public void init() {


        var root = getViewModel().getRoot();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);

        var title = getViewModel().getTitle();
        title.getStyleClass().add("history-title");
        root.getChildren().add(title);

        var infoBox = new BorderPane();
        root.getChildren().add(infoBox);
        infoBox.setLeft(getViewModel().getInfoUser());
        infoBox.setPadding(new Insets(0, 20, 0, 20));

        var infoUser = getViewModel().getInfoUser();
        infoUser.setStyle("-fx-font-size: 16px; -fx-font-weight: bold");

        var printButton = getViewModel().getPrintButton();
        getViewModel().createPrintButton();
        printButton.getStyleClass().add("btn");
        infoBox.setRight(printButton);

        getViewModel().getScPane().setFitToWidth(true);
        root.getChildren().add(getViewModel().getScPane());

        getViewModel().showFixedBills();
    }

    @Override
    public @NotNull Node getView() {

        return getViewModel().getRoot();
    }
}

