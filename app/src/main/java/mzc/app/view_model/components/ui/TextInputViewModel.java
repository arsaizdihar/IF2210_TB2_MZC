package mzc.app.view_model.components.ui;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;
import mzc.app.view.components.ui.FormGroupView;
import mzc.app.view_model.base.BaseViewModel;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TextInputViewModel extends BaseViewModel {
    @Getter
    @NotNull
    private static ValidationSupport validationSupport = new ValidationSupport();

    @Getter
    @NotNull TextField textField = new TextField();

    @Getter
    @NotNull HBox fieldContainer = new HBox();

    @NotNull
    private Map<String, String> validations = new HashMap<>();

    @Getter
    FormGroupView formGroup;

    public void init() {
        createForm();
        this.fieldContainer.getChildren().add(this.textField);
        HBox.setHgrow(this.textField, Priority.ALWAYS);
        validationSupport.registerValidator(this.textField, false, (Validator<String>) (control, s) -> {
            for (var entry : validations.entrySet()) {
                var regex = entry.getKey();
                var message = entry.getValue();
                var condition = s.matches(regex);
                if (!condition) {
                    return ValidationResult.fromErrorIf(control, message, true);
                }
            }
            return ValidationResult.fromErrorIf(control, "", false);
        });
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

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*\\.?\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d.]", ""));
                }
            });
        }
    }

    public void addValidation(String regex, String message) {
        validations.put(regex, message);
    }

    public boolean isValid() {
        return !validationSupport.isInvalid();
    }

    public static boolean isAllValid(TextInputViewModel[] textInputViewModels) {
        for (var textInputViewModel : textInputViewModels) {
            if (!textInputViewModel.isValid()) {
                return false;
            }
        }
        return true;
    }
}
