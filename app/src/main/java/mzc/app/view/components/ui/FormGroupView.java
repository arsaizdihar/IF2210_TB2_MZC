package mzc.app.view.components.ui;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.ui.FormGroupViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(FormGroupViewModel.class)
public class FormGroupView extends BaseView<FormGroupViewModel> {
    public FormGroupView(Node input) {
        super();
        getViewModel().setInput(input);
    }
    @Override
    public @NotNull Node getView() {
        getViewModel().getMain().getStyleClass().add("form-group");
        getViewModel().getLabel().getStyleClass().add("form-group-label");
        return getViewModel().getMain();
    }

    public void setLabel(String str) {
        getViewModel().getLabel().setText(str);
    }
}
