package mzc.app.adapter;

import mzc.app.adapter.json.JSONAdapter;
import mzc.app.adapter.orm.ORMAdapter;
import org.junit.jupiter.api.*;

public class AdapterManagerTest {
    private static AdapterManager adapterManager;

    @AfterEach
    public void closeSession() {
        if (adapterManager != null)
            adapterManager.adapter.close();
    }
    @Test
    public void testJSON() {
        adapterManager = new AdapterManager(new JSONAdapter());
        Assertions.assertTrue(adapterManager.getAdapter() instanceof JSONAdapter);
    }

    @Test
    public void testORM() {
        adapterManager = new AdapterManager(new ORMAdapter());
        Assertions.assertTrue(adapterManager.getAdapter() instanceof ORMAdapter);
    }

    @Test
    public void testList() {
        var res = AdapterManager.getAvailableAdapters();
        Assertions.assertEquals(res.get("JSON"), JSONAdapter.class);
        Assertions.assertEquals(res.get("ORM"), ORMAdapter.class);
    }
}
