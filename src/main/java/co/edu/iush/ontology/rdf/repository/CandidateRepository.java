package co.edu.iush.ontology.rdf.repository;

import co.edu.iush.ontology.rdf.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT DISTINCT c FROM Candidate c LEFT JOIN FETCH c.skills LEFT JOIN FETCH c.qualifications")
    List<Candidate> findAllWithSkillsAndQualifications();

}