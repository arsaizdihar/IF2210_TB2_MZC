package mzc.app.view_model.components.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.adapter.base.AdapterType;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import mzc.app.view.components.FolderDialogView;
import mzc.app.view.components.ui.FormGroupView;
import mzc.app.view.components.ui.TextInputView;

public class DataStoreViewModel extends SettingsTabViewModel {
    @Getter
    private final AppSetting setting = AppSettingManager.get();
    @Getter
    private final Label leftSubtitle = new Label("Pilih metode penyimpanan data");
    @Getter
    private final VBox selectMethod = new VBox();
    @Getter
    private final ToggleGroup storageMethod = new ToggleGroup();
    @Getter
    private final RadioButton json = new RadioButton("JSON");
    @Getter
    private final RadioButton xml = new RadioButton("XML");
    @Getter
    private final RadioButton obj = new RadioButton("OBJ");
    @Getter
    private final RadioButton sqlRaw = new RadioButton("SQL - Raw Query");
    @Getter
    private final RadioButton sqlOrm = new RadioButton("SQL - ORM");
    @Getter
    private final VBox storageLocation = new VBox();
    @Getter
    final HBox jsonBox = new HBox();
    @Getter
    final Label jsonLocation = new Label(setting.getJSONPath());
    @Getter
    final FolderDialogView jsonFolder = new FolderDialogView(new Button("Pilih Folder"),
            folder -> {
                jsonLocation.setText(folder.getAbsolutePath());
            }
    );
    @Getter
    final HBox xmlBox = new HBox();
    @Getter
    final Label xmlLocation = new Label(setting.getXMLPath());
    @Getter
    final FolderDialogView xmlFolder = new FolderDialogView(new Button("Pilih Folder"),
            folder -> {
                xmlLocation.setText(folder.getAbsolutePath());
            }
    );
    @Getter
    final HBox objBox = new HBox();
    @Getter
    final Label objLocation = new Label(setting.getOBJPath());
    @Getter
    final FolderDialogView objFolder = new FolderDialogView(new Button("Pilih Folder"),
            folder -> {
                objLocation.setText(folder.getAbsolutePath());
            }
    );
    @Getter
    TextInputView sqlRawInput;
    @Getter
    TextInputView sqlOrmInput;

    public DataStoreViewModel() {
        super();
    }

    @Override
    public void init() {
        super.init();
        json.setToggleGroup(storageMethod);
        json.getStyleClass().add("toggle-btn");
        xml.setToggleGroup(storageMethod);
        xml.getStyleClass().add("toggle-btn");
        obj.setToggleGroup(storageMethod);
        obj.getStyleClass().add("toggle-btn");
        sqlRaw.setToggleGroup(storageMethod);
        sqlRaw.getStyleClass().add("toggle-btn");
        sqlOrm.setToggleGroup(storageMethod);
        sqlOrm.getStyleClass().add("toggle-btn");

        var currentStorageMethod = setting.getStorageMethod();

        if (currentStorageMethod.equals(AdapterType.JSON)) {
            json.setSelected(true);
        } else if (currentStorageMethod.equals(AdapterType.XML)) {
            xml.setSelected(true);
        } else if (currentStorageMethod.equals(AdapterType.OBJ)) {
            obj.setSelected(true);
        } else if (currentStorageMethod.equals(AdapterType.SQLRaw)) {
            sqlRaw.setSelected(true);
        } else if (currentStorageMethod.equals(AdapterType.SQLORM)) {
            sqlOrm.setSelected(true);
        }

        getSaveButtonL().setOnAction(
                event -> {
                    if (json.isSelected()) {
                        setting.setStorageMethod(AdapterType.JSON);
                    } else if (xml.isSelected()) {
                        setting.setStorageMethod(AdapterType.XML);
                    } else if (obj.isSelected()) {
                        setting.setStorageMethod(AdapterType.OBJ);
                    } else if (sqlRaw.isSelected()) {
                        setting.setStorageMethod(AdapterType.SQLRaw);
                    } else if (sqlOrm.isSelected()) {
                        setting.setStorageMethod(AdapterType.SQLORM);
                    }
                    setting.save();
                    showInfoAlert(true);
                }
        );

        createView(jsonFolder);
        createView(xmlFolder);
        createView(objFolder);
        jsonLocation.setPadding(new Insets(0, 0, 0, 4));
        xmlLocation.setPadding(new Insets(0, 0, 0, 4));
        objLocation.setPadding(new Insets(0, 0, 0, 4));

        jsonBox.getChildren().addAll(jsonFolder.getView(), jsonLocation);
        jsonBox.setAlignment(Pos.CENTER_LEFT);
        xmlBox.getChildren().addAll(xmlFolder.getView(), xmlLocation);
        xmlBox.setAlignment(Pos.CENTER_LEFT);
        objBox.getChildren().addAll(objFolder.getView(), objLocation);
        objBox.setAlignment(Pos.CENTER_LEFT);

        sqlRawInput = new TextInputView("Lokasi penyimpanan SQL Raw", false);
        createView(sqlRawInput);
        sqlRawInput.getViewModel().getTextField().setText(setting.getSqlRawDatabaseUrl());

        sqlOrmInput = new TextInputView("Lokasi penyimpanan SQL ORM", false);
        createView(sqlOrmInput);
        sqlOrmInput.getViewModel().getTextField().setText(setting.getSqlOrmDatabaseUrl());

        getSaveButtonR().setOnAction(
                event -> {
                    setting.setJSONPath(jsonLocation.getText());
                    setting.setXMLPath(xmlLocation.getText());
                    setting.setOBJPath(objLocation.getText());
                    setting.setSqlRawDatabaseUrl(sqlRawInput.getViewModel().getVal());
                    setting.setSqlOrmDatabaseUrl(sqlOrmInput.getViewModel().getVal());
                    setting.save();
                    showInfoAlert(true);
                }
        );
        leftSubtitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        selectMethod.getChildren().addAll(leftSubtitle, json, xml, obj, sqlRaw, sqlOrm);
        var jsonForm = new FormGroupView(jsonBox);
        jsonForm.setLabel("Lokasi penyimpanan JSON");
        createView(jsonForm);
        var xmlForm = new FormGroupView(xmlBox);
        xmlForm.setLabel("Lokasi penyimpanan XML");
        createView(xmlForm);
        var objForm = new FormGroupView(objBox);
        objForm.setLabel("Lokasi penyimpanan OBJ");
        createView(objForm);


        storageLocation.getChildren().addAll(jsonForm.getView(), xmlForm.getView(), objForm.getView(), sqlRawInput.getView(), sqlOrmInput.getView());
    }
}
