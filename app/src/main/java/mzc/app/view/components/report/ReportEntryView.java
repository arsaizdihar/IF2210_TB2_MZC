package mzc.app.view.components.report;

import javafx.geometry.Insets;
import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.model.FixedBill;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.report.ReportEntryViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ReportEntryViewModel.class)
public class ReportEntryView extends BaseView<ReportEntryViewModel> {

    public ReportEntryView(FixedBill fixedBill) {
        super();
        getViewModel().setFixedBill(fixedBill);
    }

    @Override
    public @NotNull Node getView() {
        getViewModel().createPaymentInfo();
        getViewModel().createTable();
        getViewModel().getRoot().setPadding(new Insets(10));

        return getViewModel().getRoot();
    }

}
