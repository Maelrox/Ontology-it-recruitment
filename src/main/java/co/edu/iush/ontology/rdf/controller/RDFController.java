package co.edu.iush.ontology.rdf.controller;

import co.edu.iush.ontology.rdf.service.RDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RDFController {

    @Autowired
    private RDFService rdfService;

    @PostMapping("/query")
    public ResponseEntity<Object> queryRDF(@RequestBody String sparqlQuery) {
        try {
            return ResponseEntity.ok().body(rdfService.executeQuery(sparqlQuery));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

}
