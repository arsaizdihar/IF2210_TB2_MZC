package mzc.app.adapter.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.file.IFileDataLoader;
import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XMLLoader implements IFileDataLoader {
    private static final XmlMapper mapper = new XmlMapper();

    @Override
    public <T extends BaseModel> @NotNull Map<String, T> loadData(Class<T> model) {
        Path path = getPathForModel(model);
        String absolutePath = path.toAbsolutePath().toString();
        File inputFile = new File(absolutePath);
        XMLInputFactory f = XMLInputFactory.newFactory();
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            XMLStreamReader sr = f.createXMLStreamReader(inputStream);
            ArrayList<T> arr = new ArrayList<>();
            sr.next();
            sr.next();
            while (sr.hasNext()) {
                try {
                    T item = mapper.readValue(sr, model);
                    arr.add(item);
                    if (sr.hasNext())
                        sr.next();
                } catch (Exception ignored) {
                }
            }
            Map<String, T> result = new HashMap<>();
            arr.forEach(item -> result.put(Long.toString(item.getId()), item));
            return result;
        } catch (IOException | XMLStreamException e) {
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
    public <T extends BaseModel> void commit(Map<String, T> data, Class<T> model) {
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            StringWriter out = new StringWriter();
            XMLStreamWriter sw = xmlOutputFactory.createXMLStreamWriter(out);
            XmlMapper mapper = new XmlMapper(xmlInputFactory);
            sw.writeStartDocument();
            sw.writeStartElement("root");
            for (T item : data.values()) {
                mapper.writeValue(sw, item);
            }
            sw.writeEndElement();
            sw.writeEndDocument();
            Path path = getPathForModel(model);
            String absolutePath = path.toAbsolutePath().toString();
            File file = new File(absolutePath);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(out.toString());
            }
        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getPathForModel(Class<?> model) {
        return Paths.get(AdapterConfig.getBaseDataPath() + model.getSimpleName() + "Data.xml");
    }
}
