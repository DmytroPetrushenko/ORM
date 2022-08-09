package org.knubisoft.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Person {
    private Long id;
    private String name;
    private LocalDate birthDay;

    @Override
    public String toString() {
        return "Person{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", birthDay=" + birthDay
                + '}';
    }
}
