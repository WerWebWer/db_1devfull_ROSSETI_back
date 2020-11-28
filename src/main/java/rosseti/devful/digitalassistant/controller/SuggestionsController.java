package rosseti.devful.digitalassistant.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rosseti.devful.digitalassistant.model.entities.EfficiencySuggestion;
import rosseti.devful.digitalassistant.model.resources.SuggestionResource;
import rosseti.devful.digitalassistant.service.SuggestionService;
import rosseti.devful.digitalassistant.utils.DocumentGenerator;

import java.util.List;

@CrossOrigin
@RestController
public class SuggestionsController {

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private DocumentGenerator documentGenerator;


    @PostMapping("/api/savesuggestion/")
    public String saveSuggestion(@RequestBody SuggestionResource suggestionResource) {
        EfficiencySuggestion efficiencySuggestion = suggestionService.saveSuggestion(suggestionResource);
        return efficiencySuggestion.getRegNumber();
    }

    @GetMapping("/api/getsuggestion/")
    public List<EfficiencySuggestion> getSuggestions() {
        return suggestionService.getAll();
    }

    @GetMapping("/api/getsuggestion/{id}")
    public EfficiencySuggestion getSuggestion(@PathVariable(value="id") String id) {
        return suggestionService.getById(id);
    }

    @RequestMapping(value = "/api/getfile/{id}", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public ResponseEntity<FileSystemResource> myGetFile(@PathVariable(value="id") String id) {
        EfficiencySuggestion efficiencySuggestion = suggestionService.getById(id);
        SuggestionResource suggestionResource =
                suggestionService.getResourceByRegNumber(efficiencySuggestion.getRegNumber());
        FileSystemResource fileSystemResource =
                new FileSystemResource(documentGenerator.generate(efficiencySuggestion, suggestionResource));
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<FileSystemResource>(fileSystemResource, headers, HttpStatus.OK);
    }
}
