package mzc.app.view_model.components.member_list;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import mzc.app.view.components.ui.TextInputView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import mzc.app.view_model.components.ui.TextInputViewModel;
import org.jetbrains.annotations.NotNull;

public class EditMemberViewModel extends RightSideViewModel {
    @Getter
    @Setter
    private Customer customer;
    @Getter
    private VBox root = new VBox();
    @Getter
    private HBox detailsBox;
    @Getter
    final @NotNull VBox customerInfoBox = new VBox();
    @Getter
    final @NotNull Label title = new Label("Edit Member");
    @Getter
    private TextInputView name = new TextInputView("Nama", 200, false);
    @Getter
    private TextInputView phoneNumber = new TextInputView("Nomor Handphone", 200, true);
    @Getter
    private ComboBox<CustomerType> categoryField = new ComboBox<>();
    @Getter
    private VBox categoryDropdown;
    @Getter
    private Button editCustomerButton;

    @Override
    public void init() {
        super.init();
        LeftSideMemberListViewModel.ReloadContext reload = useContext(LeftSideMemberListViewModel.ReloadContext.class).getValue();
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        customerDetails();
        editCustomerButton = new Button("Kirim");
        editCustomerButton.setOnAction(
                event -> {
                    if (!TextInputViewModel.isAllValid(new TextInputViewModel[]{name.getViewModel(), phoneNumber.getViewModel()})) {
                        return;
                    }

                    customer.setName(name.getViewModel().getVal().trim());
                    customer.setPhone(phoneNumber.getViewModel().getVal());
                    customer.setType(categoryField.getValue());
                    getAdapter().getCustomer().persist(customer);
                    root.getChildren().clear();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pengubahan Data Member Berhasil");
                    alert.setHeaderText(null);
                    alert.setContentText("Silahkan tutup laman informasi ini");
                    alert.show();

                    reload.reload();

                }
        );
        editCustomerButton.setPrefWidth(100);
        editCustomerButton.getStyleClass().addAll("btn", "btn-success");
        editCustomerButton.setStyle("-fx-font-weight: bold;");
        root.setAlignment(Pos.CENTER);

        var editableCustomerInfoBox = new HBox(customerInfoBox);
        editableCustomerInfoBox.setAlignment(Pos.CENTER);
        editableCustomerInfoBox.setSpacing(60);
        root.getChildren().addAll(title, editableCustomerInfoBox, editCustomerButton);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(100);
        root.setStyle("-fx-font-size: 16px;");
    }

    private void customerDetails() {
        name.getViewModel().addValidation(
                "^\\s*(\\S+\\s*)+$",
                "Nama tidak boleh kosong"
        );
        name.getViewModel().addValidation(
                "^.{0,50}$",
                "Nama tidak boleh lebih dari 50 karakter"
        );
        name.getViewModel().getTextField().setText(customer.getName());
        phoneNumber.getViewModel().getTextField().setText(customer.getPhone());

        phoneNumber.getViewModel().addValidation(
                "^\\+?\\d{10,15}$",
                "Nomor Handphone tidak valid"
        );
        createView(name);
        createView(phoneNumber);

        Label categoryLabel = new Label("Tipe Anggota");

        categoryField.getItems().add(CustomerType.MEMBER);
        categoryField.getItems().add(CustomerType.VIP);
        categoryField.setPrefWidth(200);
        if (customer.getType() == null || customer.getType() == CustomerType.BASIC) {
            customer.setType(CustomerType.MEMBER);
        } else {
            categoryField.setValue(customer.getType());
        }
        categoryDropdown = new VBox(categoryLabel, categoryField);

        customerInfoBox.getChildren().addAll(name.getView(), phoneNumber.getView(), categoryDropdown);
        customerInfoBox.setSpacing(15);
    }
}
