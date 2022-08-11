package org.knubisoft.strategy;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import org.knubisoft.model.Person;
import org.knubisoft.util.EntityReflectionUtil;

public class StrategyCsv implements Strategy {
    private final String template = "(([\\w\\d\\-]+[,])+[\\w\\d\\-]+\\b)";
    private final EntityReflectionUtil entityReflectionUtil = new EntityReflectionUtil();

    @Override
    @SneakyThrows
    public <T extends Person> List<T> reader(File file, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] keyNameArray = reader.readNext();
            for (String[] values : reader) {
                Map<String, String> nameAndValuesMap = IntStream.range(0, keyNameArray.length)
                        .boxed()
                        .collect(Collectors.toMap(i -> keyNameArray[i], i -> values[i]));
                result.add(entityReflectionUtil.createEntity(nameAndValuesMap, clazz));
            }
        }
        return result;
    }

    @Override
    public boolean isApplyable(String content) {
        return content.matches(template);
    }
}
