package org.knubisoft;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.knubisoft.injection.Injector;
import org.knubisoft.model.Person;
import org.knubisoft.strategy.Strategy;

public class FileOrm {
    private final List<Strategy> injectedStrategy;

    public FileOrm() {
        injectedStrategy = new Injector().injection();
    }

    public <T extends Person> List<T> transform(File file, Class<T> clazz) {
        Strategy strategy = findOutTypeFile(file);
        return strategy.reader(file, clazz);
    }

    @SneakyThrows
    private Strategy findOutTypeFile(File file) {
        String result = FileUtils.readFileToString(file, StandardCharsets.UTF_8)
                .replaceAll("[\\n\\r]", "");
        return injectedStrategy.stream()
                .filter(strategy -> strategy.isApplyable(result))
                .findFirst().orElseThrow(() -> {
                    throw new UnsupportedOperationException("This file: " + file
                            + " was not supported!");
                });
    }
}
