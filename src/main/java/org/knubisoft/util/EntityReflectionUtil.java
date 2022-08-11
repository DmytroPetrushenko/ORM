package org.knubisoft.util;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.knubisoft.model.Person;

public class EntityReflectionUtil {

    @SneakyThrows
    public <T extends Person> T createEntity(Map<String,String> map, Class<T> clazz) {
        T instance = clazz.getConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String value = map.get(field.getName());
            Class<?> type = field.getType();
            field.set(instance, formatStringToFieldType(type, value));
        }
        return instance;
    }

    private Object formatStringToFieldType(Class<?> type, String value) {
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
