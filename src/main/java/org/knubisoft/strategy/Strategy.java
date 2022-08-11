package org.knubisoft.strategy;

import java.io.File;
import java.util.List;
import org.knubisoft.model.Person;
import org.knubisoft.util.FileContentTypeEnum;

public interface Strategy {
    <T extends Person> List<T> reader(File file, Class<T> clazz);

    FileContentTypeEnum getEnum();
}
