package mzc.chart;

import mzc.app.bootstrap.App;
import mzc.app.bootstrap.PageEntry;
import mzc.app.modules.plugins.Plugin;
import mzc.chart.view.CustomerChartView;
import mzc.chart.view.SalesChartView;

public class ChartPlugin extends Plugin {
    public ChartPlugin() {
        super("Chart");
    }

    @Override
    public void setup(App appContext) {
        var salesEntry = new PageEntry("Sales Chart", "sales-chart", SalesChartView.class);
        appContext.getPages().put(salesEntry.getKey(), salesEntry);

        var customerEntry = new PageEntry("Customer Chart", "customer-chart", CustomerChartView.class);
        appContext.getPages().put(customerEntry.getKey(), customerEntry);
    }

    @Override
    public void postSetup() {

    }
}
