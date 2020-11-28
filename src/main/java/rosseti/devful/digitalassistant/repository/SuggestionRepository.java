package rosseti.devful.digitalassistant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import rosseti.devful.digitalassistant.model.entities.EfficiencySuggestion;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<EfficiencySuggestion, Long> {
    List<EfficiencySuggestion> findByRegNumber(String regNumber);
    EfficiencySuggestion findById(String id);

}