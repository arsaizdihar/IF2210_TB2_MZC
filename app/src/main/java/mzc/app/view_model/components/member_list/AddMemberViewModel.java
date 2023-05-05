package mzc.app.view_model.components.member_list;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.annotation.ModelInject;
import mzc.app.view.components.ui.TextInputView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import mzc.app.view_model.components.ui.TextInputViewModel;
import org.jetbrains.annotations.NotNull;

@Getter
public class AddMemberViewModel extends RightSideViewModel {
    @Getter
    private VBox main;
    @Getter
    private HBox mainCol;
    @Getter
    private VBox list;

    @Override
    public void init() {
        super.init();
        Label tambahBarang = new Label("Tambah Anggota");
        tambahBarang.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        setupLines();
        Button kirim = new Button("Kirim");
        kirim.setPrefWidth(100);
        kirim.getStyleClass().addAll("btn", "btn-success");
        kirim.setStyle("-fx-font-weight: bold;");
        this.mainCol = new HBox(this.list);
        this.mainCol.setAlignment(Pos.CENTER);
        this.mainCol.setSpacing(100);
        this.main = new VBox(tambahBarang, this.mainCol, kirim);
        this.main.setAlignment(Pos.CENTER);
        this.main.setSpacing(100);
        this.main.setStyle("-fx-font-size: 16px;");
    }

    private void setupLines() {
        TextInputView nama = new TextInputView("Nama", 200, false);
        createView(nama);
        TextInputView nomorHandphone = new TextInputView("Nomor Handphone", 200, false);
        createView(nomorHandphone);
        Label kategori = new Label("Tipe Anggota");

        ComboBox<String> kategoriField = new ComboBox<>(FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3"
        ));
        kategoriField.setPrefWidth(200);
        VBox kat = new VBox(kategori, kategoriField);

        this.list = new VBox(nama.getView(), nomorHandphone.getView(), kat);
        this.list.setSpacing(15);
    }

}

