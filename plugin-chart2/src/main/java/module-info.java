open module mzc.summary {
    requires transitive mzc.app;

    provides mzc.app.modules.plugins.Plugin with mzc.summary.PiePlugin;
}