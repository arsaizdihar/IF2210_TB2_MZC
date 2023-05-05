package mzc.app.view_model.components.product_list;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.Getter;
import mzc.app.annotation.ModelInject;
import mzc.app.model.Product;
import mzc.app.view.components.FileDialogView;
import mzc.app.view.components.ui.TextInputView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Getter
public class RightSideProductViewModel extends RightSideViewModel {
    private VBox main;
    private HBox mainCol;
    private VBox list;
    private VBox image = new VBox();

    @Override
    public void init() {
        super.init();

        Label tambahBarang = new Label("Tambah Barang");
        tambahBarang.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        setupLines();
        setupImage();
        Button kirim = new Button("Kirim");
        kirim.setPrefWidth(100);
        kirim.getStyleClass().addAll("btn", "btn-success");
        kirim.setStyle("-fx-font-weight: bold;");
        this.mainCol = new HBox(this.list, this.image);
        this.mainCol.setAlignment(Pos.CENTER);
        this.mainCol.setSpacing(100);
        this.main = new VBox(tambahBarang, this.mainCol, kirim);
        this.main.setAlignment(Pos.CENTER);
        this.main.setSpacing(100);
        this.main.setStyle("-fx-font-size: 16px;");
    }

    private void setupLines() {
        TextInputView namaBarang = new TextInputView("Nama Barang", 200);
        createView(namaBarang);
        Label kategori = new Label("Kategori");

        ComboBox<String> kategoriField = new ComboBox<>(FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3"
        ));
        kategoriField.setPrefWidth(200);
        VBox kat = new VBox(kategori, kategoriField);
        TextInputView hargaBeli = new TextInputView("Harga Beli", 200);
        createView(hargaBeli);
        TextInputView hargaJual = new TextInputView("Harga Jual", 200);
        createView(hargaJual);
        TextInputView stok = new TextInputView("Stok", 200);
        createView(stok);
        this.list = new VBox(namaBarang.getView(), kat, hargaBeli.getView(), hargaJual.getView(), stok.getView());
        this.list.setSpacing(15);
    }

    private void setupImage() {
        Image placeholder = (new Image(Objects.requireNonNull(getClass().getResource("/mzc/app/assets/product.png")).toExternalForm()));
        ImageView imageView = new ImageView(placeholder);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        this.image.getChildren().add(imageView);
        Button pilihGambar = new Button("Pilih Gambar");
        Label error = new Label("File tidak valid");
        var fileDialogView = new FileDialogView(pilihGambar, file -> {
            try {
                Image image = new Image(new FileInputStream(file));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                this.image.getChildren().add(error);
            }
            String absolutePath = file.getAbsolutePath();
            Product product = new Product();
            try {
                product.updateImage(absolutePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        createView(fileDialogView);
        pilihGambar.getStyleClass().add("btn");


        this.image.getChildren().add(fileDialogView.getView());
    }
}

