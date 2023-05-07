package mzc.app.view_model.components.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;
import mzc.app.view.components.ui.FormGroupView;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

@Getter
public class TextInputViewModel extends BaseViewModel {
    @Getter
    @NotNull TextField textField = new TextField();

    @Getter
    @NotNull HBox fieldContainer = new HBox();

    @Getter
    FormGroupView formGroup;

    public void init() {
        createForm();
        this.fieldContainer.getChildren().add(this.textField);
        HBox.setHgrow(this.textField, Priority.ALWAYS);
    }

    private void createForm() {
        if (this.formGroup == null) {
            this.formGroup = new FormGroupView(this.fieldContainer);
            createView(formGroup);
        }
    }

    public void setter(String str, int width) {
        setter(str);
        this.textField.setPrefWidth(width);
    }

    public void setter(String str) {
        createForm();
        this.getFormGroup().getViewModel().getLabel().setText(str);
        this.textField.setPromptText(str);
    }

    public String getVal() {
        return this.textField.getText();
    }

    public void numberOnly(boolean bool) {

        if (bool) {
            textField.setText("0");

            textField.textProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("\\d*\\.?\\d*")) {
                        textField.setText(newValue.replaceAll("[^\\d*\\.?\\d*]", ""));
                    }
                }
            });
        }
    }

}
