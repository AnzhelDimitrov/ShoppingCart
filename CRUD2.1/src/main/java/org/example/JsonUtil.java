package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void toJsonFile(String filePath, List<T> objects) throws IOException {
        objectMapper.writeValue(new File(filePath), objects);
    }

    public static <T> List<T> fromJsonFile(String filePath, Class<T> type) throws IOException {
        return objectMapper.readValue(
                new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, type)
        );
    }
}
