package mzc.app.view_model.components.member_list;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Customer;
import mzc.app.view_model.base.BaseViewModel;

import java.util.Objects;

public class MemberViewModel extends BaseViewModel {
    @Getter @Setter
    private Customer customer;
    @Getter private final ImageView avatar = new ImageView(Objects.requireNonNull(getClass().getResource("/mzc/app/assets/avatar.png")).toExternalForm());
    @Getter private HBox memberBox = new HBox();

    @Getter private VBox memberInfo = new VBox();
    @Getter private Text customerName = new Text();
    @Getter private Label customerType = new Label();
    @Getter private Label customerPhone = new Label();
    @Getter private Label customerPoints = new Label();
    @Getter private HBox wrapperButton = new HBox();
    @Getter private Button transactionButton = new Button("Transaction");
    @Getter private Button editButton = new Button("Edit");
    @Getter private Button setActiveButton = new Button();
    @Override
    public void init() {
        // TODO : set binding and context
        super.init();
        reload();
    }
    public void reload() {
        memberBox.setSpacing(10);
        setActiveButton.setOnAction(
                event -> {
                    if (customer.isDeactivated()) {
                        customer.setDeactivated(false);
                        getAdapter().getCustomer().persist(customer);
                        setActiveButton.setText("Deactivate");
                    } else {
                        customer.setDeactivated(true);
                        getAdapter().getCustomer().persist(customer);
                        setActiveButton.setText("Activate");
                    }
                }
        );
        avatar.setFitWidth(64);
        avatar.setFitHeight(64);
        memberInfo.setPrefWidth(435);


        customerName.setText(customer.getName());
        customerName.setWrappingWidth(435);
        memberInfo.getChildren().add(customerName);

        customerType.setText(customer.getType().toString() + (customer.isDeactivated() ? " (deactivated)" : ""));
        memberInfo.getChildren().add(customerType);

        HBox infoSeparator = new HBox();
        infoSeparator.setBackground(Background.fill(Color.TRANSPARENT));
        infoSeparator.setPrefHeight(2);
        memberInfo.getChildren().add(infoSeparator);

        // TODO : tambah icon phone
        customerPhone.setText(customer.getPhone());
        memberInfo.getChildren().add(customerPhone);

        customerPoints.setText("Points " + customer.getPoints());
        memberInfo.getChildren().add(new Label(customerPoints.getText()));

        memberBox.getChildren().add(avatar);
        memberBox.getChildren().add(memberInfo);

        wrapperButton.getChildren().add(transactionButton);
        wrapperButton.getChildren().add(editButton);
        wrapperButton.getChildren().add(setActiveButton);
        memberBox.getChildren().add(wrapperButton);
    }
}
