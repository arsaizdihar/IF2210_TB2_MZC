package mzc.app.bootstrap;

import mzc.app.view.page.*;

import java.util.HashMap;
import java.util.Map;

public class PageEntryFactory {
    public static Map<String, PageEntry> createPageEntries() {
        Map<String, PageEntry> pageEntries = new HashMap<>();

        var productList = new PageEntry("Daftar Barang", "product_list", ProductListPageView.class);
        var memberList = new PageEntry("Daftar Member", "member_list", MemberListPageView.class);

        pageEntries.put(productList.getKey(), productList);
        pageEntries.put(memberList.getKey(), memberList);

        var cashier = new PageEntry("Kasir", "cashier", CashierPageView.class);
        pageEntries.put(cashier.getKey(), cashier);

        var report = new PageEntry("Laporan Penjualan", "report", ReportPageView.class);
        pageEntries.put(report.getKey(), report);

//        var defaultPage = createDefaultPage();
        var settingsPage = createSettingPage();
//        pageEntries.put(defaultPage.getKey(), defaultPage);
        pageEntries.put(settingsPage.getKey(), settingsPage);

        return pageEntries;
    }

    public static PageEntry createSettingPage() {
        return new PageEntry("Settings Page", "settings", SettingsPageView.class);
    }
}
