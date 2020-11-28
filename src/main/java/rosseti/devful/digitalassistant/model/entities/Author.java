package rosseti.devful.digitalassistant.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
public class Author {
    private String id;
    private String name;
    private String department;
}
