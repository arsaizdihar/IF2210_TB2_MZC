package mzc.app.adapter.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import mzc.app.model.BaseModel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JSONLoader {
    @Getter private static final Gson gson = new Gson();
    public static <T extends BaseModel> Map<String, T> loadDataFromFile(Class<T> model) {
        Path path = getPathForModel(model);
        String absolutePath = path.toAbsolutePath().toString();
        try (JsonReader reader = new JsonReader(new FileReader(absolutePath))) {
            System.out.println("Load File");
            @SuppressWarnings("unchecked") TypeToken<Map<String, T>> modelType = (TypeToken<Map<String, T>>)
                    TypeToken.getParameterized(Map.class, String.class, model);
            return gson.fromJson(reader, modelType);
        } catch (IOException e) {
            try {
                Files.createDirectories(path.getParent());
                try (FileWriter writer = new FileWriter(absolutePath)) {
                    writer.write("{}");
                }
                System.out.println("Write file");
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            return new HashMap<>();
        }
    }

    public static Path getPathForModel(Class<?> model) {
        return Paths.get("./data/" + model.getSimpleName() + "Data.json");
    }
}
