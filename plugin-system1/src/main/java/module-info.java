open module mzc.plugin_currency {
    requires transitive mzc.app;

    provides mzc.app.modules.plugins.Plugin with mzc.plugin_currency.CurrencyPlugin;

    exports mzc.plugin_currency;
    exports mzc.plugin_currency.adapter;
    exports mzc.plugin_currency.adapter.base;
    exports mzc.plugin_currency.adapter.file;
    exports mzc.plugin_currency.adapter.orm;
    exports mzc.plugin_currency.model;
}