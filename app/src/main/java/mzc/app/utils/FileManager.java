package mzc.app.utils;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import lombok.Getter;
import mzc.app.adapter.base.AdapterConfig;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class FileManager {
    @Getter
    private static final Map<String, Image> imageCache = new HashMap<>();

    @Getter
    private static final ExecutorService imageLoaderExecutor = Executors.newFixedThreadPool(4);

    private static class BufferResourceLoader {

    }

    private static final BufferResourceLoader buffer = new BufferResourceLoader();

    public static void copyFile(String src, String dst) throws IOException {
        Path dstPath = Paths.get(dst);
        if (!dstPath.getParent().toFile().exists()) {
            if (!dstPath.getParent().toFile().mkdirs()) {
                throw new IOException("Cannot create directory " + dstPath.getParent().toString());
            }
        }
        URL url;
        try {
            url = new URL(src);
        } catch (Exception e) {
            url = null;
        }
        InputStream is;

//        if path is from jar file, then use getResourceAsStream
        if (url != null && url.getProtocol().equals("jar")) {
            var urlPath = url.getPath();
            var index = urlPath.indexOf("/mzc/app/");
            var resourcePath = urlPath.substring(index);
            is = buffer.getClass().getResourceAsStream(resourcePath);
        } else {
            is = new FileInputStream(src);
        }
        if (is == null) {
            throw new IOException("Cannot open file " + src);
        }
        try (OutputStream os = new FileOutputStream(dst)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
        }
    }

    public static String getDataStorePath(String realPath) {
        Path path = Paths.get(realPath);
        return Paths.get(AdapterConfig.getBaseDataPath(), "files", path.getFileName().toString()).toAbsolutePath().toString();
    }

    public static String getRandomizedDataStorePath(String realPath) {
        String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(realPath);
        return Paths.get(AdapterConfig.getBaseDataPath(), "files", filename).toAbsolutePath().toString();
    }

    public static @NotNull String getResourcePath(String path) {
        return Objects.requireNonNull(buffer.getClass().getResource(path)).toExternalForm();
    }

    public static Image getImage(String path) {
        Image image;
        if (!path.startsWith("file:") && !path.startsWith("jar:file:")) {
            path = "file:" + path;
        }
        synchronized (imageCache) {
            image = imageCache.get(path);
        }
        if (image == null) {
            image = new Image(path);
            synchronized (imageCache) {
                imageCache.put(path, image);
            }
        }
        return image;
    }

    public static void getImageAsync(String path, Consumer<Image> callback) {
        Task<Image> task = new Task<>() {
            @Override
            protected Image call() {
                return getImage(path);
            }
        };
        task.setOnSucceeded(event -> callback.accept(task.getValue()));
        imageLoaderExecutor.submit(task);
    }

    public static Image getImageFromResource(String path) {
        return FileManager.getImage(FileManager.getResourcePath(path));
    }
}
