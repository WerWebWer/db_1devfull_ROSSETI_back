package rosseti.devful.digitalassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rosseti.devful.digitalassistant.model.entities.SuggestionResourceEntity;

import java.util.List;

public interface SuggestionResourceEntityRepository extends JpaRepository<SuggestionResourceEntity, Long> {
    List<SuggestionResourceEntity> getByRegNumber(String regNumber);
}