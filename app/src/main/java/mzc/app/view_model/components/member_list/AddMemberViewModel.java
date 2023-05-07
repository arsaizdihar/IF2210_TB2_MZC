package mzc.app.view_model.components.member_list;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import mzc.app.model.Product;
import mzc.app.view.components.ui.TextInputView;
import mzc.app.view_model.components.split_page.RightSideViewModel;

import java.math.BigDecimal;

@Getter
public class AddMemberViewModel extends RightSideViewModel {
    @Getter
    private VBox main;
    @Getter
    private HBox mainCol;
    @Getter
    private VBox list;
    @Getter private  TextInputView name = new TextInputView("Nama", 200, false);
    @Getter private TextInputView phoneNumber = new TextInputView("Nomor Handphone", 200, true);
    @Getter private ComboBox<CustomerType> categoryField = new ComboBox<>();
    @Getter private VBox categoryDropdown;
    @Getter private Button addCustomerButton;
    @Override
    public void init() {
        super.init();
        LeftSideMemberListViewModel.ReloadContext reload = useContext(LeftSideMemberListViewModel.ReloadContext.class).getValue();
        Label tambahAnggota = new Label("Tambah Anggota");
        tambahAnggota.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        setupLines();
        addCustomerButton = new Button("Kirim");
        addCustomerButton.setOnAction(event -> {
            if (name.getViewModel().getTextField().getText().isEmpty() || phoneNumber.getViewModel().getTextField().getText().isEmpty() || categoryField.getValue() == null) {
                System.out.println("Tidak boleh kosong");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Penambahan Member Gagal");
                alert.setContentText(name.getViewModel().getTextField().getText().isEmpty() ? "Nama tidak boleh kosong" : phoneNumber.getViewModel().getTextField().getText().isEmpty() ? "Nomor Handphone tidak boleh kosong" : "Kategori tidak boleh kosong");

                alert.showAndWait();
            } else {
                Customer customer = new Customer();
                customer.setName(name.getViewModel().getTextField().getText());
                customer.setPhone(phoneNumber.getViewModel().getTextField().getText());
                customer.setType(categoryField.getValue());
                getAdapter().getCustomer().persist(customer);
                System.out.println("Nama: " + name.getViewModel().getTextField().getText());
                System.out.println("Nomor Handphone: " + phoneNumber.getViewModel().getTextField().getText());
                System.out.println("Kategori: " + categoryField.getValue());
                main.getChildren().clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Penambahan Member Berhasil");
                alert.setHeaderText(null);
                alert.setContentText("Silahkan tutup laman informasi ini");
                alert.show();

                reload.reload();
            }
        });
        addCustomerButton.setPrefWidth(100);
        addCustomerButton.getStyleClass().addAll("btn", "btn-success");
        addCustomerButton.setStyle("-fx-font-weight: bold;");
        this.mainCol = new HBox(this.list);
        this.mainCol.setAlignment(Pos.CENTER);
        this.mainCol.setSpacing(60);
        this.main = new VBox(tambahAnggota, this.mainCol, addCustomerButton);
        this.main.setAlignment(Pos.CENTER);
        this.main.setSpacing(60);
        this.main.setStyle("-fx-font-size: 16px;");
    }

    private void setupLines() {
        createView(name);
        createView(phoneNumber);
        Label categoryLabel = new Label("Tipe Anggota");

        categoryField.getItems().add(CustomerType.BASIC);
        categoryField.getItems().add(CustomerType.MEMBER);
        categoryField.getItems().add(CustomerType.VIP);
        categoryField.setPrefWidth(200);
        categoryDropdown = new VBox(categoryLabel, categoryField);

        this.list = new VBox(name.getView(), phoneNumber.getView(), categoryDropdown);
        this.list.setSpacing(15);
    }
}

