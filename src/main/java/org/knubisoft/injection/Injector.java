package org.knubisoft.injection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.SneakyThrows;
import org.knubisoft.strategy.Strategy;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class Injector {
    private static final String PACKAGE = "org.knubisoft";
    private static final Class<Strategy> clazz = Strategy.class;

    @SneakyThrows
    public List<Strategy> injection() {
        List<Strategy> instanceList = new ArrayList<>();
        List<Class<? extends Strategy>> classes = findAllClassesUsingReflectionsLibrary();
        for (Class<? extends Strategy> clazz : classes) {
            instanceList.add(clazz.getConstructor().newInstance());
        }
        return instanceList;

    }

    private List<Class<? extends Strategy>> findAllClassesUsingReflectionsLibrary() {
        Reflections reflections = new Reflections(Injector.PACKAGE, Scanners.SubTypes);
        Set<Class<? extends Strategy>> classSet = reflections.getSubTypesOf(clazz);
        return new ArrayList<>(classSet);
    }
}
