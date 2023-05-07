package mzc.app.view_model.components.member_list;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Customer;
import mzc.app.utils.FileManager;
import mzc.app.view_model.base.BaseViewModel;

import java.util.Objects;

public class MemberViewModel extends BaseViewModel {
    @Getter @Setter
    private Customer customer;
    private final ImageView avatar = new ImageView(FileManager.getImageFromResource("/mzc/app/assets/user.png"));
    @Getter StackPane root = new StackPane();
    @Getter private HBox memberBox = new HBox();
    @Getter private VBox memberInfo = new VBox();
    @Getter private Text customerName = new Text();
    @Getter private Text customerType = new Text();
    @Getter private Text customerPhone = new Text();
    @Getter private Text customerPoints = new Text();
    private final ImageView edit = new ImageView(FileManager.getImageFromResource("/mzc/app/assets/edit.png"));
    private final ImageView history = new ImageView(FileManager.getImageFromResource("/mzc/app/assets/list-1.png"));
    private final ImageView active = new ImageView(FileManager.getImageFromResource("/mzc/app/assets/checkmark.png"));
    private final ImageView inactive = new ImageView(FileManager.getImageFromResource("/mzc/app/assets/cross.png"));
    @Getter private Button transactionButton = new Button();
    @Getter private Button editButton = new Button();
    @Getter private Button setActiveButton = new Button();
    @Override
    public void init() {
        super.init();
        LeftSideMemberListViewModel.ReloadContext reload = useContext(LeftSideMemberListViewModel.ReloadContext.class).getValue();
        setActiveButton.setOnAction(
                event -> {
                    if (customer.isDeactivated()) {
                        customer.setDeactivated(false);
                        getAdapter().getCustomer().persist(customer);
                    } else {
                        customer.setDeactivated(true);
                        getAdapter().getCustomer().persist(customer);
                    }
                    reload.reload();
                }
        );
        memberBox.setSpacing(5);

        avatar.setFitWidth(90);
        avatar.setFitHeight(90);
        memberBox.getChildren().add(avatar);

        customerName.setText(customer.getName());
        memberInfo.getChildren().add(customerName);

        customerType.setText(customer.getType().toString() + (customer.isDeactivated() ? " (deactivated)" : ""));
        customerName.setWrappingWidth(490);
        memberInfo.getChildren().add(customerType);

        HBox infoSeparator = new HBox();
        infoSeparator.setBackground(Background.fill(Color.TRANSPARENT));
        infoSeparator.setPrefHeight(4);
        memberInfo.getChildren().add(infoSeparator);

        customerPhone.setText(customer.getPhone() + " (📞)");
        memberInfo.getChildren().add(customerPhone);

        customerPoints.setText("Points " + customer.getPoints());
        memberInfo.getChildren().add(new Label(customerPoints.getText()));

        memberInfo.setSpacing(2);
        memberBox.getChildren().add(memberInfo);

        HBox icons = new HBox(history, edit, customer.isDeactivated() ? active : inactive);
        icons.setAlignment(Pos.TOP_RIGHT);
        icons.setSpacing(5);
        icons.setPadding(new Insets(5, 0, 5, 0));
        HBox buttons = new HBox(transactionButton, editButton, setActiveButton);
        buttons.setAlignment(Pos.TOP_RIGHT);
        buttons.setSpacing(5);
        buttons.setPadding(new Insets(5, 0, 5, 0));
        root.getChildren().add(memberBox);
        root.getChildren().add(icons);
        root.getChildren().add(buttons);

        customerName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        customerType.setStyle("-fx-font-size: 10px; -fx-text-fill: gray");

        history.setFitWidth(20);
        history.setFitHeight(20);
        edit.setFitWidth(20);
        edit.setFitHeight(20);
        active.setFitWidth(20);
        active.setFitHeight(20);
        inactive.setFitWidth(20);
        inactive.setFitHeight(20);
        transactionButton.setOpacity(0);
        transactionButton.setPrefSize(20, 20);
        editButton.setOpacity(0);
        editButton.setPrefSize(20, 20);
        setActiveButton.setOpacity(0);
        setActiveButton.setPrefSize(20, 20);
    }
}
