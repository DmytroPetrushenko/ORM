package org.knubisoft.strategy;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.util.List;
import org.knubisoft.model.Person;

public class StrategyXml implements Strategy {

    @Override
    public <T extends Person> List<T> reader(File file, Class<T> clazz) {
        XStream xstream = new XStream();
        xstream.alias("entity", Person.class);
        xstream.alias("entities", List.class);
        xstream.allowTypesByWildcard(new String[]{"org.knubisoft.**"});
        return (List<T>) xstream.fromXML(file);
    }
}
