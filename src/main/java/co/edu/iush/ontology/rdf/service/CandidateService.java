package co.edu.iush.ontology.rdf.service;

import co.edu.iush.ontology.rdf.dto.ErrorResponseDTO;
import co.edu.iush.ontology.rdf.exception.ERecruitmentException;
import co.edu.iush.ontology.rdf.model.Candidate;
import co.edu.iush.ontology.rdf.model.Qualification;
import co.edu.iush.ontology.rdf.model.Skill;
import co.edu.iush.ontology.rdf.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Validated
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    private final Validator validator;

    public CandidateService(Validator validator) {
        this.validator = validator;
    }

    // Method to list candidates by filter
    public List<Candidate> listCandidatesByFilter(String filter) {

        List<Candidate> listCandidates = candidateRepository.findAll();
        return listCandidates;
    }

    // Method to update a candidate
    public Candidate updateCandidate(Long candidateId, Candidate updatedCandidate) throws Exception {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + candidateId));
        validateProperties(updatedCandidate);
        updateEditableProperties(candidate, updatedCandidate);

        return candidateRepository.save(candidate);
    }

    // Method to create a candidate
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    // Method to delete a candidate
    public void deleteCandidate(Long candidateId) {
        // Check if the candidate exists
        if (!candidateRepository.existsById(candidateId)) {
            throw new RuntimeException("Candidate not found with id: " + candidateId);
        }
        candidateRepository.deleteById(candidateId);
    }

    /**
     * Updates the editable properties of the given candidate with the values from the updated candidate.
     * Only the editable properties, such as name, qualifications, and skills, are updated.
     *
     * @param candidate        The candidate to be updated.
     * @param updatedCandidate The candidate containing the updated values.
     */
    private void updateEditableProperties(Candidate candidate, Candidate updatedCandidate) {
        candidate.setName(updatedCandidate.getName());

        Set<Qualification> updatedQualifications = new HashSet<>();
        for (Qualification updatedQualification : updatedCandidate.getQualifications()) {
            Qualification existingQualification = candidate.getQualifications().stream()
                    .filter(q -> q.getId().equals(updatedQualification.getId()))
                    .findFirst().orElse(null);

            if (existingQualification != null) {
                updatedQualifications.add(existingQualification);
            } else {
                updatedQualifications.add(updatedQualification);
            }
        }

        candidate.setQualifications(updatedQualifications);

        Set<Skill> updatedSkills = new HashSet<>();
        for (Skill updatedSkill : updatedCandidate.getSkills()) {
            Skill existingSkill = candidate.getSkills().stream()
                    .filter(s -> s.getId().equals(updatedSkill.getId()))
                    .findFirst().orElse(null);

            if (existingSkill != null) {
                updatedSkills.add(existingSkill);
            } else {
                updatedSkills.add(updatedSkill);
            }
        }
        candidate.setSkills(updatedSkills);

        Set<Long> updatedQualificationIds = updatedCandidate.getQualifications().stream()
                .map(Qualification::getId)
                .collect(Collectors.toSet());

        candidate.getQualifications().removeIf(existingQualification ->
                !updatedQualificationIds.contains(existingQualification.getId()));

        Set<Long> updatedSkillIds = updatedCandidate.getSkills().stream()
                .map(Skill::getId)
                .collect(Collectors.toSet());

        candidate.getSkills().removeIf(existingSkill ->
                !updatedSkillIds.contains(existingSkill.getId()));
    }

    /*
        Validate properties
     */
    private void validateProperties(Candidate candidate) throws ERecruitmentException {
        Errors errors = new BeanPropertyBindingResult(candidate, "Candidate");
        validator.validate(candidate, errors);

        if (errors.hasErrors()) {
            ERecruitmentException ex = new ERecruitmentException();
            ex.setErrorList(new ArrayList<>());
            for (ObjectError error: errors.getAllErrors()) {
                ErrorResponseDTO invalidFieldError = new ErrorResponseDTO(error.getDefaultMessage());
                ex.getErrorList().add(invalidFieldError);
            }
            throw ex;
        }
    }


}
