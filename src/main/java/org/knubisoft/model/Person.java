package org.knubisoft.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "entities")
public class Person {
    private Long id;
    private String name;
    private LocalDate birthDay;
}
