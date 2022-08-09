package org.knubisoft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.knubisoft.model.Person;
import org.knubisoft.util.FileContentTypeEnum;

public class FileOrm {
    public static <T extends Person> List<T> transform(File file,
                                                       FileContentTypeEnum type,
                                                       Class<T> clazz) {
        switch (type) {
            case CSV:
                return readerCsv(file, clazz);
            case JSON:
                return readerJson(file);
            case XML:
                return readXml(file, clazz);
            default:
                throw new UnsupportedOperationException("This type of file: " + type
                        + " was not supported!");
        }
    }

    private static <T extends Person> List<T> readXml(File file, Class<T> clazz) {
        XStream xstream = new XStream();
        xstream.alias("entity", Person.class);
        xstream.alias("entities", List.class);
        xstream.allowTypesByWildcard(new String[]{"org.knubisoft.**"});
        return (List<T>) xstream.fromXML(file);
    }

    private static <T extends Person> List<T> readerCsv(File file, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] values;
            reader.readNext();
            while ((values = reader.readNext()) != null) {
                result.add(createEntity(values, clazz));

            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static <T extends Person> List<T> readerJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(file, new TypeReference<List<T>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends Person> T createEntity(String[] values, Class<T> clazz) {
        try {
            T instance = clazz.getConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Class<?> type = field.getType();
                field.set(instance, formatStringToFieldType(type, values[i]));
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object formatStringToFieldType(Class<?> type, String value) {
        Map<Class<?>, Function<String, Object>> typeToFunction = new LinkedHashMap<>();
        typeToFunction.put(String.class, s -> s);
        typeToFunction.put(Float.class, Float::parseFloat);
        typeToFunction.put(Integer.class, Integer::valueOf);
        typeToFunction.put(Long.class, Long::valueOf);
        typeToFunction.put(Double.class, Double::valueOf);
        typeToFunction.put(LocalDate.class, LocalDate::parse);
        typeToFunction.put(LocalDateTime.class, LocalDateTime::parse);

        return typeToFunction.getOrDefault(type, defaultType -> {
            throw new UnsupportedOperationException("Type is not supported by parse: " + type);
        }).apply(value);
    }
}
