package rosseti.devful.digitalassistant.model.entities;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "suggestion_resources")
public class SuggestionResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reg_number")
    @JsonRawValue
    private String regNumber;

    @Column(name = "json_obj", columnDefinition = "TEXT")
    private String jsonObj;

}
