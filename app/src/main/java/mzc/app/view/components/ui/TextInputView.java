package mzc.app.view.components.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.ui.TextInputViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(TextInputViewModel.class)
public class TextInputView extends BaseView<TextInputViewModel> {

    public TextInputView(String str, int width, boolean isNumOnly) {
        getViewModel().setter(str, width); getViewModel().numberOnly(isNumOnly);
    }

    public TextInputView(String str, boolean isNumOnly) {
        getViewModel().setter(str); getViewModel().numberOnly(isNumOnly);
    }

    @Override
    public @NotNull Node getView() {
        return getViewModel().getFormGroup().getView();
    }

}
