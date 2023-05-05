package mzc.app.view_model.components.settings;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.adapter.base.AdapterType;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import mzc.app.view.components.FolderDialogView;

public class DataStoreViewModel extends SettingsTabViewModel {
    @Getter
    private final AppSetting setting = AppSettingManager.get();
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
    private final Button saveMethodButton = new Button("Simpan Perubahan");
    @Getter
    private final VBox storageLocation = new VBox();
    @Getter final HBox jsonBox = new HBox();
    @Getter final Label jsonLabel = new Label("Lokasi penyimpanan JSON");
    @Getter final Label jsonLocation = new Label(setting.getJSONPath());
    @Getter final FolderDialogView jsonFolder = new FolderDialogView(new Button("Pilih Folder"),
                folder -> {
                    jsonLocation.setText(folder.getAbsolutePath());
                }
            );
    @Getter final HBox xmlBox = new HBox();
    @Getter final Label xmlLabel = new Label("Lokasi penyimpanan XML");
    @Getter final Label xmlLocation = new Label(setting.getXMLPath());
    @Getter final FolderDialogView xmlFolder = new FolderDialogView(new Button("Pilih Folder"),
                folder -> {
                    xmlLocation.setText(folder.getAbsolutePath());
                }
            );
    @Getter final HBox objBox = new HBox();
    @Getter final Label objLabel = new Label("Lokasi penyimpanan OBJ");
    @Getter final Label objLocation = new Label(setting.getOBJPath());
    @Getter final FolderDialogView objFolder = new FolderDialogView(new Button("Pilih Folder"),
                folder -> {
                    objLocation.setText(folder.getAbsolutePath());
                }
            );
    @Getter final HBox sqlRawBox = new HBox();
    @Getter final Label sqlRawLabel = new Label("Lokasi penyimpanan SQL Raw");
    @Getter final TextField sqlRawLocation = new TextField(setting.getSqlRawDatabaseUrl());
    @Getter final HBox sqlOrmBox = new HBox();
    @Getter final Label sqlOrmLabel = new Label("Lokasi penyimpanan SQL ORM");
    @Getter final TextField sqlOrmLocation = new TextField(setting.getSqlOrmDatabaseUrl());
    @Getter
    private final Button saveLocationButton = new Button("Simpan Perubahan");
    public DataStoreViewModel() {
        super();
    }

    @Override
    public void init() {
        super.init();
        json.setToggleGroup(storageMethod);
        xml.setToggleGroup(storageMethod);
        obj.setToggleGroup(storageMethod);
        sqlRaw.setToggleGroup(storageMethod);
        sqlOrm.setToggleGroup(storageMethod);

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

        saveMethodButton.setOnAction(
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
            }
        );

        createView(jsonFolder);
        createView(xmlFolder);
        createView(objFolder);


        jsonBox.getChildren().addAll(jsonFolder.getView(), jsonLocation);
        xmlBox.getChildren().addAll(xmlFolder.getView(), xmlLocation);
        objBox.getChildren().addAll(objFolder.getView(), objLocation);
        sqlRawBox.getChildren().addAll(sqlRawLocation);
        sqlOrmBox.getChildren().addAll(sqlOrmLocation);

        saveLocationButton.setOnAction(
                event -> {
                    setting.setJSONPath(jsonLocation.getText());
                    setting.setXMLPath(xmlLocation.getText());
                    setting.setOBJPath(objLocation.getText());
                    setting.setSqlRawDatabaseUrl(sqlRawLocation.getText());
                    setting.setSqlOrmDatabaseUrl(sqlOrmLocation.getText());
                    setting.save();
                }
        );

        selectMethod.getChildren().addAll(json, xml, obj, sqlRaw, sqlOrm, saveMethodButton);
        storageLocation.getChildren().addAll(jsonLabel ,jsonBox, xmlLabel, xmlBox, objLabel, objBox, sqlRawLabel, sqlRawBox, sqlOrmLabel, sqlOrmBox, saveLocationButton);
    }
}
