package mzc.app.adapter.obj;

import lombok.NonNull;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.file.IFileDataLoader;
import mzc.app.model.BaseModel;
import mzc.app.model.Customer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class OBJLoader<T extends BaseModel> implements IFileDataLoader<T> {
    @Override
    public @NonNull Map<String, T> loadData(Class<T> model) {
        Path path = getPathForModel(model);
        String absolutePath = path.toAbsolutePath().toString();
        try (FileInputStream fileInputStream
                     = new FileInputStream(absolutePath);
             ObjectInputStream objectInputStream
                     = new ObjectInputStream(fileInputStream);) {
            @SuppressWarnings("unchecked")
            var res = (Map<String, T>) objectInputStream.readObject();
            return res;
        } catch (IOException | ClassNotFoundException e) {
            try {
                Files.createDirectories(path.getParent());
                try (FileWriter writer = new FileWriter(absolutePath)) {
                    writer.write("");
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            return new HashMap<>();
        }
    }

    @Override
    public void commit(Map<String, T> data, Class<T> model) {
        Path path = getPathForModel(model);
        String absolutePath = path.toAbsolutePath().toString();
        try (FileOutputStream fileOutputStream = new FileOutputStream(absolutePath); ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getPathForModel(Class<?> model) {
        return Paths.get(AdapterConfig.getBaseDataPath() + model.getSimpleName() + "Data.obj");
    }
}
