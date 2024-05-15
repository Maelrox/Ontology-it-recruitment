package co.edu.iush.ontology.rdf.service;

import java.util.List;
import java.util.Map;

public interface RDFService {
    public List<Map<String, String>> executeQuery(String sparqlQuery) throws Exception;
}
