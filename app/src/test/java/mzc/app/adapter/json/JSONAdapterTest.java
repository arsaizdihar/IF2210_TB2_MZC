package mzc.app.adapter.json;

import mzc.app.adapter.base.AdapterConfig;
import mzc.app.model.Customer;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JSONAdapterTest {

    @BeforeAll
    public static void setup() {
        AdapterConfig.setBaseDataPath("./data/temp/");
    }

    @Test
    public void testLoad() {
        JSONAdapter jsonAdapter = new JSONAdapter();
        Customer c = new Customer();
        jsonAdapter.getCustomer().persist(c);
        JSONAdapter jsonAdapter2 = new JSONAdapter();

        assert c.equals(jsonAdapter2.getCustomer().getById(c.getId()));

        jsonAdapter2.clearData();
    }
}
