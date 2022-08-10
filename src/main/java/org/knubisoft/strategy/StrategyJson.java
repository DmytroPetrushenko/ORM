package org.knubisoft.strategy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.util.List;
import lombok.SneakyThrows;
import org.knubisoft.model.Person;

public class StrategyJson implements Strategy {

    @Override
    @SneakyThrows
    public  <T extends Person> List<T> reader(File file, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(file, new TypeReference<List<T>>() {});
    }
}
