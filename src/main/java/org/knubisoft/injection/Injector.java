package org.knubisoft.injection;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.knubisoft.FileOrm;
import org.knubisoft.strategy.Strategy;
import org.knubisoft.util.FileContentTypeEnum;
import org.knubisoft.util.MyVisitor;

public class Injector {
    private static final String PACKAGE = "org.knubisoft";
    private static final String FIRST_INDEX = "org";
    private static final String LAST_INDEX = ".class";
    private final List<List<Path>> pathList = new ArrayList<>();

    @SneakyThrows
    public Map<FileContentTypeEnum, Strategy> injection() {
        ClassLoader classLoader = FileOrm.class.getClassLoader();
        Enumeration<URL> resources = classLoader.getResources(PACKAGE.replace(".", "/"));

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            File file = new File(url.getFile());
            Path path = Path.of(file.getPath());
            MyVisitor myVisitor = new MyVisitor();
            Files.walkFileTree(path, myVisitor);
            pathList.add(myVisitor.getClassList());
        }

        return pathList.stream()
                .flatMap(Collection::stream)
                .map(this::getClass)
                .map(this::getInstanceByInject)
                .filter(Optional::isPresent)
                .map(optional -> (Strategy) optional.get())
                .collect(Collectors.toMap(Strategy::getEnum, Function.identity()));
    }

    private Class<?> getClass(Path value) {
        try {
            String valueString = value.toString();
            int firstIndex = valueString.indexOf(FIRST_INDEX);
            int lastIndex = valueString.indexOf(LAST_INDEX);
            String substring = valueString.substring(firstIndex, lastIndex)
                    .replace("\\", ".");
            return Class.forName(substring);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private Optional<Object> getInstanceByInject(Class<?> clazz) {
        Optional<Annotation> result = Arrays.stream(clazz.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Inject.class))
                .findFirst();
        if (result.isPresent()) {
            return Optional.of(clazz.getConstructor().newInstance());
        }
        return Optional.empty();
    }
}
