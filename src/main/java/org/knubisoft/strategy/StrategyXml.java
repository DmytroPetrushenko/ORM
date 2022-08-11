package org.knubisoft.strategy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.util.List;
import lombok.SneakyThrows;
import org.knubisoft.model.Person;

public class StrategyXml implements Strategy {
    private final String template = "\\<\\?xml.+\\>\\<\\w+s\\>.*\\<\\/\\w+s\\>";

    @Override
    @SneakyThrows
    public <T extends Person> List<T> reader(File file, Class<T> clazz) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        return xmlMapper.readValue(file, new TypeReference<>() {
        });
    }

    @Override
    public boolean isApplyable(String content) {
        return content.matches(template);
    }
}
