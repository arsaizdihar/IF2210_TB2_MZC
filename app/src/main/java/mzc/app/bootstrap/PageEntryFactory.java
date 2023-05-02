package mzc.app.bootstrap;

import mzc.app.view.page.MainPageView;

import java.util.HashMap;
import java.util.Map;

public class PageEntryFactory {
    public static Map<String, PageEntry> createPageEntries() {
        Map<String, PageEntry> pageEntries = new HashMap<>();

        var defaultPage = createDefaultPage();
        pageEntries.put(defaultPage.getKey(), defaultPage);

        return pageEntries;
    }

    public static PageEntry createDefaultPage() {
        return new PageEntry("Main Page", "main", MainPageView.class);
    }
}
