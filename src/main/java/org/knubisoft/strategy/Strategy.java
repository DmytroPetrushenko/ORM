package org.knubisoft.strategy;

import java.io.File;
import java.util.List;
import org.knubisoft.model.Person;

public interface Strategy {
    <T extends Person> List<T> reader(File file, Class<T> clazz);

    boolean isApplyable(String content);
}
