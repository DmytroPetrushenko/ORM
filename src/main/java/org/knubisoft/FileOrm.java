package org.knubisoft;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.knubisoft.injection.Injector;
import org.knubisoft.model.Person;
import org.knubisoft.strategy.Strategy;
import org.knubisoft.util.FileContentTypeEnum;

public class FileOrm {
    private final Map<FileContentTypeEnum, Strategy> injectedStrategy;

    public FileOrm() {
        injectedStrategy = new Injector().injection();
    }

    public <T extends Person> List<T> transform(File file, Class<T> clazz) {
        FileContentTypeEnum typeEnum = findOutTypeFile(file);
        Strategy strategy = injectedStrategy.get(typeEnum);
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
