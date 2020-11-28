package rosseti.devful.digitalassistant.utils;

import org.springframework.stereotype.Component;
import rosseti.devful.digitalassistant.model.Status;
import rosseti.devful.digitalassistant.model.entities.EfficiencySuggestion;
import rosseti.devful.digitalassistant.model.resources.SuggestionResource;

import java.util.Calendar;

@Component
public class Mappers {

    public EfficiencySuggestion suggestionFromResource(SuggestionResource suggestionResource) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(suggestionResource.getCreateDate());
        int year = calendar.get(Calendar.YEAR);
        EfficiencySuggestion efficiencySuggestion = new EfficiencySuggestion();
        efficiencySuggestion.setId(suggestionResource.getId());
        efficiencySuggestion.setRegNumber(getRegNumber(year));
        efficiencySuggestion.setCreateDate(suggestionResource.getCreateDate());
        efficiencySuggestion.setCreateYear(year);
        efficiencySuggestion.setName(suggestionResource.getName());
        efficiencySuggestion.setAuthors(suggestionResource.getAuthors());
        efficiencySuggestion.setAuthorsPositions(suggestionResource.getAuthorsPositions());
        efficiencySuggestion.setProposalScope(suggestionResource.getScope());
        efficiencySuggestion.setProposalCategory(suggestionResource.getCategory());
        efficiencySuggestion.setStatus(Status.CREATED);
        efficiencySuggestion.setStatusUpdateDate(suggestionResource.getCreateDate());
        // сохраняем SuggestionResource с тем же номером в таблицу

        return efficiencySuggestion;
    }

    private String getRegNumber(int year) {

        String numberStub = "111";
        return year + "-10-" + numberStub;
    }
}
