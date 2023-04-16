package mzc.app.adapter;

import mzc.app.adapter.obj.OBJLoader;
import mzc.app.model.Customer;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LoaderTest {

//    @Test
//    public void testOBJ() {
//        Customer customer = new Customer("a", "09");
//        Map<String, Customer> map = new HashMap<>();
//        map.put(Long.toString(customer.getId()), customer);
//        Path path = OBJLoader.getPathForModel(Customer.class);
//        String absolutePath = path.toAbsolutePath().toString();
//        try (FileOutputStream fileOutputStream = new FileOutputStream(absolutePath); ObjectOutputStream objectOutputStream
//                = new ObjectOutputStream(fileOutputStream)) {
//            objectOutputStream.writeObject(map);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try (FileInputStream fileInputStream
//                     = new FileInputStream(absolutePath);
//             ObjectInputStream objectInputStream
//                     = new ObjectInputStream(fileInputStream);) {
//            @SuppressWarnings("unchecked")
//            Map<String, Customer> map2 = (Map<String, Customer>) objectInputStream.readObject();
//            assert map2.size() == 1;
//            assert map2.get("0").equals(customer);
//            map2.put("2", customer);
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
