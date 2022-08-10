package org.knubisoft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVReader;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.knubisoft.model.Person;
import org.knubisoft.strategy.Strategy;
import org.knubisoft.strategy.StrategyCsv;
import org.knubisoft.strategy.StrategyJson;
import org.knubisoft.strategy.StrategyXml;
import org.knubisoft.util.FileContentTypeEnum;

public class FileOrm {
    private Strategy strategy;
    public <T extends Person> List<T> transform(File file,
                                                       Class<T> clazz) {
        FileContentTypeEnum typeEnum = findOutTypeFile(file);
        switch (typeEnum) {
            case CSV:
                strategy = new StrategyCsv();
                break;
            case JSON:
                strategy = new StrategyJson();
                break;
            case XML:
                strategy = new StrategyXml();
                break;
            default:
                throw new RuntimeException("Unknown type" + typeEnum);
        }
        return strategy.reader(file, clazz);
    }

    @SneakyThrows
    private FileContentTypeEnum findOutTypeFile(File file) {
        String result = FileUtils.readFileToString(file, StandardCharsets.UTF_8)
                .replaceAll("[\\n\\r]", "");
        return Arrays.stream(FileContentTypeEnum.values())
                .filter(e -> result.matches(e.getPattern()))
                .findFirst().orElseThrow(() -> {
                    throw new UnsupportedOperationException("This file: " + file
                            + " was not supported!");
                });
    }
}
