package org.knubisoft;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knubisoft.model.Person;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

class FileOrmTest {
    private static Person personCsv1;
    private static Person personCsv2;
    private static Person personJson1;
    private static Person personJson2;
    private static Person personXml1;
    private static Person personXml2;
    private static URL resourceCsv;
    private static File fileCsv;
    private static URL resourceJson;
    private static File fileJson;
    private static URL resourceXml;
    private static File fileXml;

    @BeforeAll
    static void beforeAll() {
        personCsv1 = new Person(1L, "Alex", LocalDate.parse("2005-10-25"));
        personCsv2 = new Person(2L, "Bob", LocalDate.parse("1994-03-03"));
        personJson1 = new Person(1L, "Alice", LocalDate.parse("2001-12-30"));
        personJson2 = new Person(2L, "Bob", LocalDate.parse("2012-02-10"));
        personXml1 = new Person(1L, "Bob", LocalDate.parse("1981-07-24"));
        personXml2 = new Person(2L, "Alice", LocalDate.parse("1985-04-14"));
        resourceCsv = FileOrm.class.getClassLoader().getResource("test.csv");
        fileCsv = new File(Objects.requireNonNull(resourceCsv).getPath());
        resourceJson = FileOrm.class.getClassLoader().getResource("file.json");
        fileJson = new File(Objects.requireNonNull(resourceJson).getPath());
        resourceXml = FileOrm.class.getClassLoader().getResource("fileXML.xml");
        fileXml = new File(Objects.requireNonNull(resourceXml).getPath());
    }

    @Test
    void transformCsv() {
        List<Person> resultListCsv = FileOrm.transform(fileCsv, Person.class);
        Assertions.assertEquals(personCsv1, resultListCsv.get(0));
        Assertions.assertEquals(personCsv2, resultListCsv.get(1));
    }

    @Test
    void transformJson() {
        List<Person> resultListJson = FileOrm.transform(fileJson, Person.class);
        Assertions.assertEquals(personJson1, resultListJson.get(0));
        Assertions.assertEquals(personJson2, resultListJson.get(1));
    }

    @Test
    void transformXml() {
        List<Person> resultListXml = FileOrm.transform(fileXml, Person.class);
        Assertions.assertEquals(personXml1, resultListXml.get(0));
        Assertions.assertEquals(personXml2, resultListXml.get(1));
    }
}