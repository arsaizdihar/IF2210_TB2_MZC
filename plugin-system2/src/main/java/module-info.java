open module mzc.plugin_charge {
    requires transitive mzc.app;

    exports mzc.plugin_charge.view_model;
    exports mzc.plugin_charge.view;
    exports mzc.plugin_charge.models;
    exports mzc.plugin_charge.pricing;

    provides mzc.app.modules.plugins.Plugin with mzc.plugin_charge.ChargePlugin;
}