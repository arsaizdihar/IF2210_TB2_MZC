package mzc.chart.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.chart.view_model.CustomerChartViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(CustomerChartViewModel.class)
public class CustomerChartView extends PageView<CustomerChartViewModel> {
    @Override
    public @NotNull Node getView() {
        var chart = getViewModel().getStackedBarChart();
        chart.setPadding(new Insets(60));
        return chart;
    }
}
