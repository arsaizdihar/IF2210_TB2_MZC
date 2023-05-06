package mzc.app.view_model.components.member_list;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
    @Getter private VBox kat;
    @Getter private Button addCustomerButton;
    @Override
    public void init() {
        super.init();
        Label tambahBarang = new Label("Tambah Anggota");
        tambahBarang.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        setupLines();
        addCustomerButton = new Button("Kirim");
        addCustomerButton.setOnAction(event -> {
            if (name.getViewModel().getTextField().getText().isEmpty() || phoneNumber.getViewModel().getTextField().getText().isEmpty() || categoryField.getValue() == null) {
                System.out.println("Tidak boleh kosong");
            } else {
                Customer customer = new Customer();
                customer.setName(name.getViewModel().getTextField().getText());
                customer.setPhone(phoneNumber.getViewModel().getTextField().getText());
                customer.setType(categoryField.getValue());
                getAdapter().getCustomer().persist(customer);
                System.out.println("Nama: " + name.getViewModel().getTextField().getText());
                System.out.println("Nomor Handphone: " + phoneNumber.getViewModel().getTextField().getText());
                System.out.println("Kategori: " + categoryField.getValue());
            }
        });
        addCustomerButton.setPrefWidth(100);
        addCustomerButton.getStyleClass().addAll("btn", "btn-success");
        addCustomerButton.setStyle("-fx-font-weight: bold;");
        this.mainCol = new HBox(this.list);
        this.mainCol.setAlignment(Pos.CENTER);
        this.mainCol.setSpacing(100);
        this.main = new VBox(tambahBarang, this.mainCol, addCustomerButton);
        this.main.setAlignment(Pos.CENTER);
        this.main.setSpacing(100);
        this.main.setStyle("-fx-font-size: 16px;");

        setOnButtonClicked((e) -> {
            Customer customer = new Customer(this.name.getViewModel().getVal(), this.phoneNumber.getViewModel().getVal(), this.categoryField.getValue());
            getAdapter().getCustomer().persist(customer);
            System.out.println("Saved!");
            this.main = new VBox();
        });
    }

    private void setupLines() {
        createView(name);
        createView(phoneNumber);
        Label kategori = new Label("Tipe Anggota");

        categoryField.getItems().add(CustomerType.BASIC);
        categoryField.getItems().add(CustomerType.MEMBER);
        categoryField.getItems().add(CustomerType.VIP);
        categoryField.setPrefWidth(200);
        kat = new VBox(kategori, categoryField);

        this.list = new VBox(name.getView(), phoneNumber.getView(), kat);
        this.list.setSpacing(15);
    }
    public void setOnButtonClicked(EventHandler<ActionEvent> handler) {
        if (this.addCustomerButton == null) {
            throw new RuntimeException("Must set button first");
        }
        this.addCustomerButton.setOnAction(handler);
    }
}

