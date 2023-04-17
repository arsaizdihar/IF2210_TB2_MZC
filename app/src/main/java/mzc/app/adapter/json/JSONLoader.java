package mzc.app.adapter.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.NoArgsConstructor;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.file.IFileDataLoader;
import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
class JSONLoader implements IFileDataLoader {
    private static final Gson gson = new Gson();

    public <T extends BaseModel> @NotNull Map<String, T> loadData(Class<T> model) {
        Path path = getPathForModel(model);
        String absolutePath = path.toAbsolutePath().toString();
        try (JsonReader reader = new JsonReader(new FileReader(absolutePath))) {
            @SuppressWarnings("unchecked") TypeToken<Map<String, T>> modelType = (TypeToken<Map<String, T>>)
                    TypeToken.getParameterized(Map.class, String.class, model);
            return gson.fromJson(reader, modelType);
        } catch (IOException e) {
            try {
                Files.createDirectories(path.getParent());
                try (FileWriter writer = new FileWriter(absolutePath)) {
                    writer.write("{}");
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            return new HashMap<>();
        }
    }

    @Override
    public <T extends BaseModel> void commit(Map<String, T> data, Class<T> model) {
        Path path = getPathForModel(model);
        String absolutePath = path.toAbsolutePath().toString();
        try (FileWriter writer = new FileWriter(absolutePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getPathForModel(Class<?> model) {
        return Paths.get(AdapterConfig.getBaseDataPath() + model.getSimpleName() + "Data.json");
    }
}
