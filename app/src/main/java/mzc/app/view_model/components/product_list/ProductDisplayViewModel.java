package mzc.app.view_model.components.product_list;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.Getter;
import mzc.app.model.Product;
import mzc.app.view_model.base.BaseViewModel;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class ProductDisplayViewModel extends BaseViewModel {

    private Image productImage;
    private HBox mainRow = new HBox();
    private StackPane main = new StackPane();

    private Label namaBarang;
    private Label kategori;
    private Label hargaBeli;
    private Label hargaJual;
    private Label stok;

    private ImageView edit = new ImageView(Objects.requireNonNull(getClass().getResource("/mzc/app/assets/edit.png")).toExternalForm());
    private ImageView bin = new ImageView(Objects.requireNonNull(getClass().getResource("/mzc/app/assets/bin.png")).toExternalForm());

    private Button editButton = new Button();
    private Button binButton = new Button();
    @Override
    public void init() {
        super.init();
        ImageView imageView = new ImageView(this.productImage);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        this.mainRow.getChildren().add(imageView);

        VBox mainCol = new VBox();
        HBox nama = new HBox();
        nama.getChildren().add(this.namaBarang);

        mainCol.getChildren().add(nama);
        mainCol.getChildren().add(this.kategori);
        mainCol.getChildren().add(this.stok);

        HBox harga = new HBox(this.hargaBeli, this.hargaJual);
        mainCol.getChildren().add(harga);
        mainCol.setSpacing(5);

        HBox icons = new HBox(this.edit, this.bin);
        icons.setAlignment(Pos.TOP_RIGHT);
        icons.setSpacing(5);
        icons.setPadding(new Insets(5, 0, 5, 0));
        HBox buttons = new HBox(this.editButton, this.binButton);
        buttons.setAlignment(Pos.TOP_RIGHT);
        buttons.setSpacing(5);
        buttons.setPadding(new Insets(5, 0, 5, 0));
        this.mainRow.getChildren().add(mainCol);
        this.mainRow.setSpacing(10);

        this.main.getChildren().add(this.mainRow);
        this.main.getChildren().add(icons);
        this.main.getChildren().add(buttons);
        setstyle();

    }

    public void setter(Product product) {
        namaBarang = new Label(product.getName());
        kategori = new Label(product.getCategory());
        hargaBeli = new Label("Beli "+product.getBuyPrice().toString());
        hargaJual = new Label(" Jual "+product.getPrice().toString());
        stok = new Label("Stok "+Integer.toString(product.getStock()));
        productImage = product.getImage();
    }

    private void setstyle() {
        namaBarang.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        kategori.setStyle("-fx-font-size: 10px; -fx-text-fill: gray");
        edit.setFitWidth(20);
        edit.setFitHeight(20);
        bin.setFitWidth(20);
        bin.setFitHeight(20);
        editButton.setVisible(false);
        binButton.setVisible(false);
        editButton.setPrefSize(20, 20);
        binButton.setPrefSize(20, 20);
    }
}
