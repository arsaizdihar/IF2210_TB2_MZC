package mzc.chart.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.chart.view_model.SalesChartViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SalesChartViewModel.class)
public class SalesChartView extends PageView<SalesChartViewModel> {
    @Override
    public @NotNull Node getView() {
        var chart = getViewModel().getLineChart();
        chart.setPadding(new Insets(60));
        return chart;
    }
}
