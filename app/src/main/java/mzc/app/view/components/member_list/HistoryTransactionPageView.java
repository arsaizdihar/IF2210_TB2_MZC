package mzc.app.view.components.member_list;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mzc.app.annotation.ModelInject;
import mzc.app.model.Customer;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.member_list.HistoryTransactionPageViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(HistoryTransactionPageViewModel.class)
public class HistoryTransactionPageView extends RightSideView<HistoryTransactionPageViewModel> {

    public HistoryTransactionPageView(Customer customer) {
        super();
        getViewModel().setCustomer(customer);
        System.out.println("Masuk");
    }
    @Override
    public @NotNull Node getView() {

        var root = getViewModel().getRoot();
        root.setAlignment(Pos.TOP_CENTER);

        var title = getViewModel().getTitle();
        title.getStyleClass().add("history-title");
        root.getChildren().add(title);

        var infoBox = getViewModel().getInfoBox();
        root.getChildren().add(infoBox);

        var spacer = getViewModel().getSpaser();
        infoBox.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        var infoUser = getViewModel().getInfoUser();
        spacer.getChildren().add(infoUser);
        infoUser.getStyleClass().add("info-user-history");

        var printButton = getViewModel().getPrintButton();
        infoBox.getChildren().add(printButton);
        printButton.getStyleClass().add("btn");

        var listView = getViewModel().getListView();
        root.getChildren().add(listView);
        VBox.setVgrow(listView, Priority.ALWAYS);






//        button.setPrefWidth(200);
//        button.setPrefHeight(50);
//        button.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//        main.getChildren().add(button);
        return getViewModel().getRoot();
    }
}

