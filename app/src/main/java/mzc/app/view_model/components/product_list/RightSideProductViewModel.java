package mzc.app.view_model.components.product_list;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import lombok.Getter;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import org.jetbrains.annotations.NotNull;

@Getter
public class RightSideProductViewModel extends RightSideViewModel {
    private VBox main;
    private HBox mainCol;
    private VBox list;
    private VBox image;

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
        TextInput namaBarang = new TextInput("Nama Barang", 200);
        Label kategori = new Label("Kategori");
        ComboBox<String> kategoriField = new ComboBox<>(FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3"
        ));
        kategoriField.setPrefWidth(200);
        VBox kat = new VBox(kategori, kategoriField);
        TextInput hargaBeli = new TextInput("Harga Beli", 200);
        TextInput hargaJual = new TextInput("Harga Jual", 200);
        TextInput stok = new TextInput("Stok", 200);
        this.list = new VBox(namaBarang.getMain(), kat, hargaBeli.getMain(), hargaJual.getMain(), stok.getMain());
        this.list.setSpacing(15);
    }

    private void setupImage() {
        Button pilihGambar = new Button("Pilih Gambar");
        pilihGambar.getStyleClass().add("btn");

        this.image = new VBox(pilihGambar);
    }
}

@Getter
class TextInput {
    private final @NotNull Label label;
    private final @NotNull TextField textField;
    private final @NotNull VBox main;

    public TextInput(String str, int width) {
        this.label = new Label(str);
        this.textField = new TextField();
        this.textField.setPromptText(str);
        this.textField.setPrefWidth(width);
        this.main = new VBox(this.label, this.textField);
    }
}