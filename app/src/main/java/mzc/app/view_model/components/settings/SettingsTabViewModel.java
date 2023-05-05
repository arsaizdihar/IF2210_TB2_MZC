package mzc.app.view_model.components.settings;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

public abstract class SettingsTabViewModel extends BaseViewModel {

    @NotNull
    @Getter
    private final GridPane root = new GridPane();
    @Getter
    @NotNull
    private final HBox leftBox = new HBox();

    @Getter
    @NotNull
    private final HBox rightBox = new HBox();
    @Getter
    @NotNull
    private final Label leftTitle = new Label();
    @Getter

    private final Label rightTitle = new Label();

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

    public SettingsTabViewModel() { }
}
