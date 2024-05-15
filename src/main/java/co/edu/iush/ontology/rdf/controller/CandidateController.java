package co.edu.iush.ontology.rdf.controller;

import co.edu.iush.ontology.rdf.model.Candidate;
import co.edu.iush.ontology.rdf.service.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/candidate") // Set the base path for the controller
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<Candidate>> listCandidatesByFilter(@RequestParam(required = false) String filter) {
        List<Candidate> candidates = candidateService.listCandidatesByFilter(filter);
        return ResponseEntity.ok(candidates);
    }

    // Update a candidate
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long id, @RequestBody Candidate candidateDetails) throws Exception {
        return ResponseEntity.ok(candidateService.updateCandidate(id, candidateDetails));
    }

    // Create a candidate
    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) throws Exception {
        return new ResponseEntity<>(candidateService.createCandidate(candidate), HttpStatus.CREATED);
    }

    // Delete a candidate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) throws Exception {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

}
