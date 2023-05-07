package mzc.app.view_model.components.product_list;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Product;
import mzc.app.utils.FileManager;
import mzc.app.view.components.FileDialogView;
import mzc.app.view.components.ui.TextInputView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import mzc.app.view_model.components.ui.TextInputViewModel;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

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
    private TextInputView kategoriField;

    private Image imageFile;
    private String imagePath;

    @Override
    public void init() {

        Label ubahDataBarang = new Label("Ubah Data Barang");
        ubahDataBarang.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        setupLines();
        setupImage();
        this.addButton = new Button("Kirim");
        this.addButton.setPrefWidth(100);
        this.addButton.getStyleClass().addAll("btn", "btn-success");
        this.addButton.setStyle("-fx-font-weight: bold;");
        this.mainCol = new HBox(this.listInput, this.imageHolder);
        this.mainCol.setAlignment(Pos.CENTER);
        this.mainCol.setSpacing(100);
        this.main = new VBox(ubahDataBarang, this.mainCol, addButton);
        this.main.setAlignment(Pos.CENTER);
        this.main.setSpacing(60);
        this.main.setStyle("-fx-font-size: 16px;");

        LeftSideProductViewModel.ReloadContext reload = useContext(LeftSideProductViewModel.ReloadContext.class).getValue();
        setOnButtonClicked((e) -> {
            try {
                if (!TextInputViewModel.isAllValid(new TextInputViewModel[]{namaBarang.getViewModel(), stok.getViewModel(), hargaBeli.getViewModel(), hargaJual.getViewModel(), kategoriField.getViewModel()})) {
                    return;
                }
                product.setName(this.namaBarang.getViewModel().getVal().trim());
                product.setStock(Integer.parseInt(this.stok.getViewModel().getVal()));
                product.setPrice(new BigDecimal(this.hargaJual.getViewModel().getVal()));
                product.setBuyPrice(new BigDecimal(this.hargaBeli.getViewModel().getVal()));
                product.setCategory(this.kategoriField.getViewModel().getVal());
                try {
                    product.updateImage(this.imagePath);
                } catch (IOException ex) {
                    System.out.println("Image not found");
                }
                getAdapter().getProduct().persist(product);
                System.out.println("Saved!");
                this.main.getChildren().clear();
                reload.reload();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Angka tidak valid");
                alert.showAndWait();
            }
        });
    }

    private void setupLines() {
        var categories = getAdapter().getProduct().getCategories();

        namaBarang = new TextInputView("Nama Barang", 200, false);
        namaBarang.getViewModel().getTextField().setText(product.getName());
        namaBarang.getViewModel().addValidation(
                "^\\s*(\\S+\\s*)+$",
                "Nama barang tidak boleh kosong"
        );
        namaBarang.getViewModel().addValidation(
                "^.{0,50}$",
                "Nama barang tidak boleh lebih dari 50 karakter"
        );
        createView(namaBarang);

        kategoriField = new TextInputView("Kategori", 200, false);
        kategoriField.getViewModel().getTextField().setText(product.getCategory());
        kategoriField.getViewModel().addValidation(
                "^\\s*(\\S+\\s*)+$",
                "Kategori barang tidak boleh kosong"
        );
        kategoriField.getViewModel().addValidation(
                "^.{0,50}$",
                "Kategori barang tidak boleh lebih dari 50 karakter"
        );
        createView(kategoriField);
        TextFields.bindAutoCompletion(kategoriField.getViewModel().getTextField(), categories);

        hargaBeli = new TextInputView("Harga Beli", 200, true);
        hargaBeli.getViewModel().addValidation(
                ".+",
                "Harga jual harus diisi"
        );
        hargaBeli.getViewModel().getTextField().setText(product.getBuyPrice().toString());
        createView(hargaBeli);
        hargaJual = new TextInputView("Harga Jual", 200, true);
        hargaJual.getViewModel().getTextField().setText(product.getPrice().toString());
        hargaJual.getViewModel().addValidation(
                ".+",
                "Harga jual harus diisi"
        );
        createView(hargaJual);
        stok = new TextInputView("Stok", 200, true);
        stok.getViewModel().addValidation(
                "^\\d+$",
                "Stok harus berupa angka"
        );
        stok.getViewModel().getTextField().setText(Integer.toString(product.getStock()));
        createView(stok);
        this.listInput = new VBox(namaBarang.getView(), kategoriField.getView(), hargaBeli.getView(), hargaJual.getView(), stok.getView());
        this.listInput.setSpacing(15);
    }

    private void setupImage() {
        this.imagePath = product.getImagePath();
        ImageView imageView = new ImageView();
        product.getImageAsync(imageView::setImage);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Button pilihGambar = new Button("Pilih Gambar");
        var fileDialogViewEdit = new FileDialogView(pilihGambar, file -> {
            imageView.setImage(null);
            FileManager.getImageAsync(file.getAbsolutePath(), imageView::setImage);
            this.imagePath = file.getAbsolutePath();
        }, "Image Files", Arrays.asList("*.png", "*.jpg", ".jpeg"));

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

