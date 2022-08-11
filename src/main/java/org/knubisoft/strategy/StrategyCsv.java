package org.knubisoft.strategy;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.knubisoft.injection.Inject;
import org.knubisoft.model.Person;
import org.knubisoft.util.EntityReflectionUtil;
import org.knubisoft.util.FileContentTypeEnum;

@Inject
public class StrategyCsv implements Strategy {
    private final FileContentTypeEnum enumType = FileContentTypeEnum.CSV;
    private final EntityReflectionUtil entityReflectionUtil = new EntityReflectionUtil();

    @Override
    @SneakyThrows
    public <T extends Person> List<T> reader(File file, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] values;
            reader.readNext();
            while ((values = reader.readNext()) != null) {
                result.add(entityReflectionUtil.createEntity(values, clazz));
            }
        }
        return result;
    }

    @Override
    public FileContentTypeEnum getEnum() {
        return enumType;
    }
}
