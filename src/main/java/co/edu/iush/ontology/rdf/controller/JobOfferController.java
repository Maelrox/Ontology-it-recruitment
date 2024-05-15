package co.edu.iush.ontology.rdf.controller;

import co.edu.iush.ontology.rdf.model.Candidate;
import co.edu.iush.ontology.rdf.model.JobOffer;
import co.edu.iush.ontology.rdf.service.CandidateService;
import co.edu.iush.ontology.rdf.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/job-offer")
public class JobOfferController {
    @Autowired
    private JobOfferService jobOfferService;

    @GetMapping
    public ResponseEntity<List<JobOffer>> listCandidatesByFilter(@RequestParam(required = false) String filter) {
        List<JobOffer> candidates = jobOfferService.listJobOffersByFilter(filter);
        return ResponseEntity.ok(candidates);
    }

    // Update a candidate
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobOffer(@PathVariable Long id, @RequestBody JobOffer jobOfferDetails) throws Exception {
        return ResponseEntity.ok(jobOfferService.updateJobOffer(id, jobOfferDetails));
    }

    // Create a candidate
    @PostMapping
    public ResponseEntity<JobOffer> createJobOffer(@RequestBody JobOffer jobOffer) throws Exception {
        return new ResponseEntity<>(jobOfferService.createJobOffer(jobOffer), HttpStatus.CREATED);
    }

    // Delete a candidate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) throws Exception {
        jobOfferService.deleteJobOffer(id);
        return ResponseEntity.noContent().build();
    }

}
