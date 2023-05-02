package mzc.app.bootstrap;

import mzc.app.view.page.ProductListPageView;

import java.util.HashMap;
import java.util.Map;

public class PageEntryFactory {
    public static Map<String, PageEntry> createPageEntries() {
        Map<String, PageEntry> pageEntries = new HashMap<>();

        var productList = new PageEntry("Daftar Barang", "product_list", ProductListPageView.class);
        pageEntries.put(productList.getKey(), productList);

        return pageEntries;
    }
}
