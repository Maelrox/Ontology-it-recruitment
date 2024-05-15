package co.edu.iush.ontology.rdf.service.impl;

import co.edu.iush.ontology.rdf.component.RDFLoader;
import co.edu.iush.ontology.rdf.service.RDFService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RDFServiceImpl implements RDFService {
    @Autowired
    private RDFLoader rdfLoader;

    public List<Map<String, String>> executeQuery(String sparqlQuery) throws Exception {
        rdfLoader.run("");
        Model rdfModel = rdfLoader.getRdfModel();
        try (QueryExecution queryExecution = QueryExecutionFactory.create(sparqlQuery, rdfModel)) {
            ResultSet resultSet = queryExecution.execSelect();
            return convertResultSetToList(resultSet);
        } catch (Exception e) {
            throw e;
        }
    }

    private List<Map<String, String>> convertResultSetToList(ResultSet resultSet) {
        List<Map<String, String>> resultList = new ArrayList<>();
        List<String> resultVars = resultSet.getResultVars();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            Map<String, String> resultMap = new HashMap<>();
            for (String var : resultVars) {
                resultMap.put(var, solution.get(var).toString());
            }
            resultList.add(resultMap);
        }
        return resultList;
    }
}
