package mzc.app.adapter;

import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.json.JSONAdapter;
import mzc.app.adapter.obj.OBJAdapter;
import mzc.app.adapter.orm.ORMAdapter;
import mzc.app.adapter.xml.XMLAdapter;
import org.junit.jupiter.api.*;

import java.util.Map;

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
    public void testSet() {
        adapterManager = new AdapterManager(new JSONAdapter());
        adapterManager.setAdapter(new ORMAdapter());
        Assertions.assertTrue(adapterManager.getAdapter() instanceof ORMAdapter);
    }

    @Test
    public void testConstructor() {
        adapterManager = new AdapterManager();
        Assertions.assertNotNull(adapterManager.getAdapter());
    }

    @Test
    public void testList() {
        Map<String, Class<? extends IMainAdapter>> res = AdapterManager.getAvailableAdapters();
        Assertions.assertEquals(res.get("JSON"), JSONAdapter.class);
        Assertions.assertEquals(res.get("XML"), XMLAdapter.class);
        Assertions.assertEquals(res.get("OBJ"), OBJAdapter.class);
        Assertions.assertEquals(res.get("ORM"), ORMAdapter.class);
    }
}
