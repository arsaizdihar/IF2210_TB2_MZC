package mzc.app.view.page;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view.base.PageView;
import mzc.app.view_model.base.PageViewModel;
import mzc.app.view_model.page.PrintOptionViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(PrintOptionViewModel.class)
public class PrintOptionView extends PageView<PrintOptionViewModel> {
    public PrintOptionView(Node node) {
        super();
        getViewModel().setNodeToPrint(node);
    }

    @Override
    public @NotNull Node getView() {
        return getViewModel().getRoot();
    }
}
