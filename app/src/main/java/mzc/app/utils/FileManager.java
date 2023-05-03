package mzc.app.utils;

import mzc.app.adapter.base.AdapterConfig;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileManager {
    public static void copyFile(String src, String dst) throws IOException {
        Path dstPath = Paths.get(dst);
        if (!dstPath.getParent().toFile().exists()) {
            if (!dstPath.getParent().toFile().mkdirs()) {
                throw new IOException("Cannot create directory " + dstPath.getParent().toString());
            }
            ;
        }
        try (InputStream is = new FileInputStream(src); OutputStream os = new FileOutputStream(dst)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    public static String getDataStorePath(String realPath) {
        Path path = Paths.get(realPath);
        return Paths.get(AdapterConfig.getBaseDataPath(), "files", path.getFileName().toString()).toAbsolutePath().toString();
    }

    public static String getRandomizedDataStorePath(String realPath) {
        Path path = Paths.get(realPath);
        String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(path.getFileName().toString());
        return Paths.get(AdapterConfig.getBaseDataPath(), "files", filename).toAbsolutePath().toString();
    }
}
