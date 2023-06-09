package mzc.app.view_model.components.product_list;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import lombok.Getter;
import mzc.app.model.Product;
import mzc.app.utils.FileManager;
import mzc.app.view_model.base.BaseViewModel;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class ProductDisplayViewModel extends BaseViewModel {

    private Product produk;
    private final HBox mainRow = new HBox();
    private final StackPane main = new StackPane();

    private Text namaBarang;
    private Label kategori;
    private Label hargaBeli;
    private Label hargaJual;
    private Label stok;

    private final ImageView edit = new ImageView(FileManager.getImageFromResource("/mzc/app/assets/edit.png"));
    private final ImageView bin = new ImageView(FileManager.getImageFromResource("/mzc/app/assets/bin.png"));

    private final Button editButton = new Button();
    private final Button binButton = new Button();
    @Override
    public void init() {
        super.init();
        ImageView imageView = new ImageView();
        produk.getImageAsync(imageView::setImage);
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
        LeftSideProductViewModel.ReloadContext reload = useContext(LeftSideProductViewModel.ReloadContext.class).getValue();
        setOnButtonClicked((e) -> {
            // edit
            System.out.println("Edit item");
        }, (e) -> {
            // delete
            System.out.println("Delete item");
            getAdapter().getProduct().delete(this.produk);
            getAdapter().getProduct().persist(this.produk);
            reload.reload();
                });
    }

    public void setter(Product product) {
        produk = product;
        namaBarang = new Text(product.getName());
        kategori = new Label(product.getCategory());
        hargaBeli = new Label("Beli "+product.getBuyPriceView());
        hargaJual = new Label(" Jual "+product.getPriceView());
        stok = new Label("Stok "+Integer.toString(product.getStock()));
    }

    private void setstyle() {
        namaBarang.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        namaBarang.setWrappingWidth(450);
        kategori.setStyle("-fx-font-size: 10px; -fx-text-fill: gray");
        edit.setFitWidth(20);
        edit.setFitHeight(20);
        bin.setFitWidth(20);
        bin.setFitHeight(20);
        editButton.setOpacity(0);
        binButton.setOpacity(0);
        editButton.setPrefSize(20, 20);
        binButton.setPrefSize(20, 20);
    }

    public void setOnButtonClicked(EventHandler<ActionEvent> handler1, EventHandler<ActionEvent> handler2) {
        this.binButton.setOnAction(handler2);
    }
}
