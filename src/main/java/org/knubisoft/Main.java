package org.knubisoft;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.knubisoft.model.Person;

public class Main {
    public static void main(String[] args) {
        URL resourceCsv = Main.class.getClassLoader().getResource("test.csv");
        File file = new File(Objects.requireNonNull(resourceCsv).getPath());
        List<Person> transformCsv = FileOrm.transform(file, Person.class);
        transformCsv.forEach(System.out::println);

        URL resourceJson = Main.class.getClassLoader().getResource("file.json");
        File fileJson = new File(Objects.requireNonNull(resourceJson).getPath());
        List<Person> transformJson = FileOrm.transform(fileJson, Person.class);
        transformJson.forEach(System.out::println);

        URL resourceXml = Main.class.getClassLoader().getResource("fileXML.xml");
        File fileXml = new File(Objects.requireNonNull(resourceXml).getPath());
        List<Person> transformXml = FileOrm.transform(fileXml, Person.class);
        transformXml.forEach(System.out::println);
    }
}
