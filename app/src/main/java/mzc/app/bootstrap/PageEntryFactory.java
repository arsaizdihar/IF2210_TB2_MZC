package mzc.app.bootstrap;

import mzc.app.view.page.CashierPageView;
import mzc.app.view.page.MainPageView;
import mzc.app.view.page.ProductListPageView;

import java.util.HashMap;
import java.util.Map;

public class PageEntryFactory {
    public static Map<String, PageEntry> createPageEntries() {
        Map<String, PageEntry> pageEntries = new HashMap<>();

        var productList = new PageEntry("Daftar Barang", "product_list", ProductListPageView.class);
        var main = new PageEntry("Main", "main", MainPageView.class);
        pageEntries.put(productList.getKey(), productList);
        pageEntries.put(main.getKey(), main);

        var cashier = new PageEntry("Kasir", "cashier", CashierPageView.class);
        pageEntries.put(cashier.getKey(), cashier);

        return pageEntries;
    }
}
