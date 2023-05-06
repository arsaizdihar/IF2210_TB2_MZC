package mzc.app.view_model.components.product_list;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Product;
import mzc.app.view.components.FileDialogView;
import mzc.app.view.components.ui.TextInputView;
import mzc.app.view_model.components.split_page.RightSideViewModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class EditProductViewModel extends RightSideViewModel {
    private Product product;
    private VBox main;
    private HBox mainCol;
    private VBox listInput;
    private VBox imageHolder = new VBox();
    private Button addButton;

    private TextInputView namaBarang;
    private TextInputView hargaBeli;
    private TextInputView hargaJual;
    private TextInputView stok;

    private ComboBox<String> kategoriField;
    private Image imageFile;
    private String imagePath;

    @Override
    public void init() {

        Label tambahBarang = new Label("Tambah Barang");
        tambahBarang.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        setupLines();
        setupImage();
        this.addButton = new Button("Kirim");
        this.addButton.setPrefWidth(100);
        this.addButton.getStyleClass().addAll("btn", "btn-success");
        this.addButton.setStyle("-fx-font-weight: bold;");
        this.mainCol = new HBox(this.listInput, this.imageHolder);
        this.mainCol.setAlignment(Pos.CENTER);
        this.mainCol.setSpacing(100);
        this.main = new VBox(tambahBarang, this.mainCol, addButton);
        this.main.setAlignment(Pos.CENTER);
        this.main.setSpacing(60);
        this.main.setStyle("-fx-font-size: 16px;");

        setOnButtonClicked((e) -> {
            product.setName(this.namaBarang.getViewModel().getVal());
            product.setStock(Integer.parseInt(this.stok.getViewModel().getVal()));
            product.setPrice(BigDecimal.valueOf(Integer.parseInt(this.hargaJual.getViewModel().getVal())));
            product.setBuyPrice(BigDecimal.valueOf(Integer.parseInt(this.hargaBeli.getViewModel().getVal())));
            product.setCategory(this.kategoriField.getValue());
            try {
                product.updateImage(this.imagePath);
            } catch (IOException ex) {
                System.out.println("Image not found");
            }
            getAdapter().getProduct().persist(product);
            System.out.println("Saved!");
            this.main.getChildren().clear();
        });
    }

    private void setupLines() {
        namaBarang = new TextInputView("Nama Barang", 200, false);
        namaBarang.getViewModel().getTextField().setText(product.getName());
        createView(namaBarang);
        Label kategori = new Label("Kategori");

        kategoriField = new ComboBox<>(FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3"
        ));
        kategoriField.setPrefWidth(200);
        kategoriField.setValue(product.getCategory());
        VBox kat = new VBox(kategori, kategoriField);
        hargaBeli = new TextInputView("Harga Beli", 200, true);
        hargaBeli.getViewModel().getTextField().setText(product.getBuyPrice().toString());
        createView(hargaBeli);
        hargaJual = new TextInputView("Harga Jual", 200, true);
        hargaJual.getViewModel().getTextField().setText(product.getPrice().toString());
        createView(hargaJual);
        stok = new TextInputView("Stok", 200, true);
        stok.getViewModel().getTextField().setText(Integer.toString(product.getStock()));
        createView(stok);
        this.listInput = new VBox(namaBarang.getView(), kat, hargaBeli.getView(), hargaJual.getView(), stok.getView());
        this.listInput.setSpacing(15);
    }

    private void setupImage() {
        Image placeholder = this.product.getImage();
        this.imagePath = product.getImagePath();
        ImageView imageView = new ImageView(placeholder);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Button pilihGambar = new Button("Pilih Gambar");
        var fileDialogViewEdit = new FileDialogView(pilihGambar, file -> {
            try {
                imageFile = new Image(new FileInputStream(file));
                imageView.setImage(imageFile);
                System.out.println(imageFile);
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File Error");
                alert.setHeaderText("File is not found or is not valid");
                alert.setContentText("Pastikan file yang dipilih merupakan file gambar yang valid");

                alert.showAndWait();
            }
            this.imagePath = file.getAbsolutePath();
            Product product = new Product();
            try {
                product.updateImage(this.imagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", ".jpeg");
        createView(fileDialogViewEdit);
        pilihGambar.getStyleClass().add("btn");

        this.imageHolder.getChildren().add(imageView);
        this.imageHolder.getChildren().add(fileDialogViewEdit.getView());
        this.imageHolder.setSpacing(10);
    }

    public void setOnButtonClicked(EventHandler<ActionEvent> handler) {
        if (this.addButton == null) {
            throw new RuntimeException("Must set button first");
        }
        this.addButton.setOnAction(handler);
    }
}
