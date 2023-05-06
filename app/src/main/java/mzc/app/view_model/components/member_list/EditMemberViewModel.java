package mzc.app.view_model.components.member_list;

import javafx.geometry.Pos;
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
import org.jetbrains.annotations.NotNull;

public class EditMemberViewModel extends RightSideViewModel  {
    @Getter @Setter
    private Customer customer;
    @Getter
    private VBox root = new VBox();
    @Getter
    private HBox detailsBox;
    @Getter final @NotNull VBox customerInfoBox = new VBox();
    @Getter final @NotNull Label title = new Label("Edit Member");
    @Getter private TextInputView name = new TextInputView("Nama", 200, false);
    @Getter private TextInputView phoneNumber = new TextInputView("Nomor Handphone", 200, true);
    @Getter private ComboBox<CustomerType> categoryField = new ComboBox<>();
    @Getter private VBox categoryDropdown;
    @Getter private Button editCustomerButton;
    @Override
    public void init()
    {
        super.init();
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        customerDetails();
        editCustomerButton = new Button("Kirim");
        editCustomerButton.setOnAction(
                event -> {
                    if (name.getViewModel().getTextField().getText().isEmpty() || phoneNumber.getViewModel().getTextField().getText().isEmpty() || categoryField.getValue() == null) {
                        System.out.println("Tidak boleh kosong");
                        // TODO tampilkan alert gagal
                    } else {
                        customer.setName(name.getViewModel().getTextField().getText());
                        customer.setPhone(phoneNumber.getViewModel().getTextField().getText());
                        customer.setType(categoryField.getValue());
                        getAdapter().getCustomer().persist(customer);
                        System.out.println("Nama: " + name.getViewModel().getTextField().getText());
                        System.out.println("Nomor Handphone: " + phoneNumber.getViewModel().getTextField().getText());
                        System.out.println("Kategori: " + categoryField.getValue());
                        root.getChildren().clear();
                        // TODO tampilkan alert berhasil
                    }
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
        name.getViewModel().getTextField().setText(customer.getName());
        phoneNumber.getViewModel().getTextField().setText(customer.getPhone());

        createView(name);
        createView(phoneNumber);

        Label categoryLabel = new Label("Tipe Anggota");

        categoryField.getItems().add(CustomerType.BASIC);
        categoryField.getItems().add(CustomerType.MEMBER);
        categoryField.getItems().add(CustomerType.VIP);
        categoryField.setPrefWidth(200);
        categoryField.setValue(customer.getType());
        categoryDropdown = new VBox(categoryLabel, categoryField);

        customerInfoBox.getChildren().addAll(name.getView(), phoneNumber.getView(), categoryDropdown);
        customerInfoBox.setSpacing(15);
    }
}
