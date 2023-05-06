package mzc.app.view_model.components.settings;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

public abstract class SettingsTabViewModel extends BaseViewModel {

    @NotNull
    @Getter
    private final GridPane root = new GridPane();
    @Getter
    @NotNull
    private final VBox leftBox = new VBox();

    @Getter
    @NotNull
    private final VBox settingsBoxL = new VBox();

    @Getter
    @NotNull
    private final VBox settingsBoxR = new VBox();

    @Getter
    @NotNull
    private final VBox rightBox = new VBox();
    @Getter
    @NotNull
    private final Label leftTitle = new Label();
    @Getter
    @NotNull
    private final Label rightTitle = new Label();

    @Getter
    @NotNull
    private final Button saveButtonL = new Button("Simpan Perubahan");

    @Getter
    @NotNull
    private final Button saveButtonR = new Button("Simpan Perubahan");

    @Override
    public void init() {
        super.init();
        var col1 = new ColumnConstraints();
        var col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        root.getColumnConstraints().addAll(col1, col2);
        var row = new RowConstraints();
        row.setPercentHeight(100);
        root.getRowConstraints().add(row);
    }

    public void showInfoAlert(boolean needRestart) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText("Perubahan Berhasil Disimpan");
        String content = "Perubahan berhasil disimpan.";
        if (needRestart) {
            content += " Aplikasi akan dimatikan untuk menerapkan perubahan.";
        }
        alert.setContentText(content);
        alert.showAndWait();
        if (needRestart) {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        }
    }

    public SettingsTabViewModel() { }
}
