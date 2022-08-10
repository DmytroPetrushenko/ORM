package org.knubisoft.strategy;

import org.knubisoft.model.Person;

import java.io.File;
import java.util.List;

public interface Strategy {
    <T extends Person> List<T> reader(File file, Class<T> clazz);
}
