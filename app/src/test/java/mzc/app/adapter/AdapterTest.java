package mzc.app.adapter;

import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.json.JSONAdapter;
import mzc.app.adapter.obj.OBJAdapter;
import mzc.app.adapter.orm.ORMAdapter;
import mzc.app.adapter.orm.SessionManager;
import mzc.app.adapter.sql.SQLAdapter;
import mzc.app.adapter.xml.XMLAdapter;
import mzc.app.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

public class AdapterTest {
    private IMainAdapter adapter;

    @BeforeAll
    public static void setup() {
        AdapterConfig.setBaseDataPath("./data/temp/");
        SessionManager.setUpdateUrl(false);
        SessionManager.getConfiguration();
    }

    @AfterEach
    public void cleanUp() {
        adapter.clearData();
        adapter.close();
    }

    @ParameterizedTest
    @ValueSource(classes = {JSONAdapter.class, XMLAdapter.class, OBJAdapter.class, SQLAdapter.class, ORMAdapter.class})
    public void testCustomer(Class<? extends IMainAdapter> adapterClass) {
        adapter = getAdapterManager(adapterClass);
        ICustomerAdapter customerAdapter = adapter.getCustomer();
        Set<Customer> customers;

        customers = customerAdapter.getAll();
        Assertions.assertEquals(0, customers.size());

        Customer c1 = new Customer("a\"", "1234");
        customerAdapter.persist(c1);
        customers = customerAdapter.getAll();

        Assertions.assertEquals(1, customers.size());
        Customer cc = customers.iterator().next();

        Assertions.assertEquals("a\"", cc.getName());
        Assertions.assertEquals("1234", cc.getPhone());
        Assertions.assertEquals(CustomerType.BASIC, cc.getType());

        Assertions.assertTrue(cc.equals(c1));

        Customer c2 = new Customer("b", "12345", CustomerType.MEMBER);
        customerAdapter.persist(c2);

        Assertions.assertTrue(c2.equals(customerAdapter.getById(c2.getId())));

        Customer c3 = new Customer("c", "123456", CustomerType.VIP);
        customerAdapter.persist(c3);

        customers = customerAdapter.getRegisteredCustomer();
        var iter = customers.iterator();

        Assertions.assertEquals(2, customers.size());
        Assertions.assertTrue(c2.equals(iter.next()));
        Assertions.assertTrue(c3.equals(iter.next()));
    }

    @ParameterizedTest
    @ValueSource(classes = {JSONAdapter.class, XMLAdapter.class, OBJAdapter.class, SQLAdapter.class, ORMAdapter.class})
    public void testBill(Class<? extends IMainAdapter> adapterClass) {
        adapter = getAdapterManager(adapterClass);
        Customer c = new Customer();
        adapter.getCustomer().persist(c);

        Assertions.assertEquals(0, adapter.getCustomer().getBills(c).size());

        Bill bill = new Bill(c);
        adapter.getBill().persist(bill);
        c = adapter.getCustomer().getById(c.getId());

        Assertions.assertTrue(bill.equals(adapter.getBill().getById(bill.getId())));

        Set<Bill> bills = adapter.getBill().getAll();

        Assertions.assertEquals(1, bills.size());
        Assertions.assertTrue(bill.equals(bills.iterator().next()));
        Assertions.assertTrue(bill.getCustomer().equals(c));

        c = adapter.getCustomer().getById(c.getId());
        Assertions.assertEquals(1, adapter.getCustomer().getBills(c).size());

        var product = new Product();
        adapter.getProduct().persist(product);

        var pbill = new ProductBill();
        pbill.setBill(bill);
        pbill.setProduct(product);


        adapter.getProductBill().persist(pbill);
        bill = adapter.getBill().getById(bill.getId());

        Assertions.assertEquals(1, adapter.getBill().getProducts(bill).size());
    }

    public IMainAdapter getAdapterManager(Class<? extends IMainAdapter> adapterClass) {
        try {
            return adapterClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
