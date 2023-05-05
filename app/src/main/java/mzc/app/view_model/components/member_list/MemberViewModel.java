package mzc.app.view_model.components.member_list;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Customer;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.page.MemberListPageViewModel;

import java.util.Objects;

public class MemberViewModel extends BaseViewModel {
    @Getter @Setter
    private Customer customer;
    @Getter private final ImageView avatar = new ImageView(Objects.requireNonNull(getClass().getResource("/mzc/app/assets/avatar.png")).toExternalForm());
    @Getter private HBox memberBox = new HBox();
    @Getter private VBox memberInfo = new VBox();
    @Getter private Button transactionButton = new Button("Transaction");
    @Getter private Button editButton = new Button("Edit");
//    @Getter private Button setActiveButton = new Button(customer.isDeactivated() ? "Activate" : "Deactivate");
    @Override
    public void init() {
        // TODO : set binding
        super.init();
        avatar.setFitWidth(50);
        avatar.setFitHeight(50);
        memberBox.setPrefHeight(30);
        memberBox.setPrefWidth(100);
        memberBox.setStyle("-fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-color: #ffffff; -fx-background-radius: 5px;");

        memberInfo.setPrefHeight(30);
        memberInfo.setPrefWidth(50);
        memberInfo.setStyle("-fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-color: #ffffff; -fx-background-radius: 5px;");
        memberInfo.getChildren().add(new Label(customer.getName()));
        memberInfo.getChildren().add(new Label(customer.getType().toString() + (customer.isDeactivated() ? " (deactivated)" : "")));
        Separator infoSeparator = new Separator(Orientation.HORIZONTAL);
        infoSeparator.setBackground(null);
        infoSeparator.setPrefHeight(5);
        memberInfo.getChildren().add(infoSeparator);
        // TODO : tambah icon phone
        memberInfo.getChildren().add(new Label(customer.getPhone()));
        memberInfo.getChildren().add(new Label("Points " + customer.getPoints()));

        memberBox.getChildren().add(avatar);
        memberBox.getChildren().add(memberInfo);
        memberBox.getChildren().add(transactionButton);
        memberBox.getChildren().add(editButton);
//        memberBox.getChildren().add(setActiveButton);
    }
}
