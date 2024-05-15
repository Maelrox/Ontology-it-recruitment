package co.edu.iush.ontology.rdf.repository;

import co.edu.iush.ontology.rdf.model.Candidate;
import co.edu.iush.ontology.rdf.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

}