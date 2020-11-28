package rosseti.devful.digitalassistant.model.resources;

import lombok.Data;
import rosseti.devful.digitalassistant.model.entities.Author;

import java.util.List;
import java.util.Map;

@Data
public class SuggestionResource {

    private String id;
    private Long createDate;
    private String branch;
    private List<String> authors;
    private List<String> authorsPositions;
    private String name;
    private String category;
    private String scope;
    private String current;
    private String solution;
    private String effect;
    private List<Map<String, String>> cost;
    private List<Map<String, String>> stages;
    private List<Map<String, String>> bounty;
    private Boolean makeEconomy;
}
