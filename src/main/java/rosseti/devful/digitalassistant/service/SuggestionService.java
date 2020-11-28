package rosseti.devful.digitalassistant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rosseti.devful.digitalassistant.model.entities.EfficiencySuggestion;
import rosseti.devful.digitalassistant.model.entities.SuggestionResourceEntity;
import rosseti.devful.digitalassistant.model.resources.SuggestionResource;
import rosseti.devful.digitalassistant.repository.SuggestionRepository;
import rosseti.devful.digitalassistant.repository.SuggestionResourceEntityRepository;
import rosseti.devful.digitalassistant.utils.Mappers;

import java.util.List;
import java.util.Map;

@Service
public class SuggestionService {

    @Autowired
    private Mappers mappers;

    @Autowired
    SuggestionRepository suggestionRepository;

    @Autowired
    SuggestionResourceEntityRepository suggestionResourceEntityRepository;

    @Transactional
    public EfficiencySuggestion saveSuggestion(SuggestionResource suggestionResource) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        EfficiencySuggestion efficiencySuggestion = mappers.suggestionFromResource(suggestionResource);
        SuggestionResourceEntity suggestionResourceEntity = new SuggestionResourceEntity();
        suggestionResourceEntity.setRegNumber(efficiencySuggestion.getRegNumber());
        try {
            suggestionResourceEntity.setJsonObj(ow.writeValueAsString(suggestionResource));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        suggestionResourceEntityRepository.save(suggestionResourceEntity);
        return suggestionRepository.save(efficiencySuggestion);
    }

    public List<EfficiencySuggestion> getAll() {
        return suggestionRepository.findAll();
    }

    public EfficiencySuggestion getById(String id) {
        return suggestionRepository.findById(id);
    }

    public SuggestionResource getResourceByRegNumber(String regNumber) {
        SuggestionResourceEntity suggestionResourceEntity = suggestionResourceEntityRepository.getByRegNumber(regNumber).get(0);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = suggestionResourceEntity.getJsonObj();
        try {
            SuggestionResource suggestionResource = mapper.readValue(jsonInString, SuggestionResource.class);
            JSONParser parser = new JSONParser();
            try {
                JSONObject json = (JSONObject) parser.parse(jsonInString);
                List<Map<String, String>> cost = (List<Map<String, String>>) json.get("cost");
                suggestionResource.setCost(cost);
                List<Map<String, String>> stages = (List<Map<String, String>>) json.get("stages");
                suggestionResource.setStages(stages);
                List<Map<String, String>> bounty = (List<Map<String, String>>) json.get("bounty");
                suggestionResource.setBounty(bounty);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return suggestionResource;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
