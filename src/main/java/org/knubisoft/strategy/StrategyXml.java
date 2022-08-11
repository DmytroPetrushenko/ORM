package org.knubisoft.strategy;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.util.List;
import org.knubisoft.injection.Inject;
import org.knubisoft.model.Person;
import org.knubisoft.util.FileContentTypeEnum;

@Inject
public class StrategyXml implements Strategy {
    private final FileContentTypeEnum enumType = FileContentTypeEnum.XML;

    @Override
    public <T extends Person> List<T> reader(File file, Class<T> clazz) {
        XStream xstream = new XStream();
        xstream.alias("entity", Person.class);
        xstream.alias("entities", List.class);
        xstream.allowTypesByWildcard(new String[]{"org.knubisoft.**"});
        return (List<T>) xstream.fromXML(file);
    }

    @Override
    public FileContentTypeEnum getEnum() {
        return enumType;
    }
}
