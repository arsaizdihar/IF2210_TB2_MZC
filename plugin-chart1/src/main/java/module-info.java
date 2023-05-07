module mzc.chart {
    requires transitive mzc.app;

    provides mzc.app.modules.plugins.Plugin with mzc.chart.ChartPlugin;
}