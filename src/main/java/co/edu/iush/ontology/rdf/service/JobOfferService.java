package co.edu.iush.ontology.rdf.service;

import co.edu.iush.ontology.rdf.dto.ErrorResponseDTO;
import co.edu.iush.ontology.rdf.exception.ERecruitmentException;
import co.edu.iush.ontology.rdf.model.Candidate;
import co.edu.iush.ontology.rdf.model.JobOffer;
import co.edu.iush.ontology.rdf.model.Qualification;
import co.edu.iush.ontology.rdf.model.Skill;
import co.edu.iush.ontology.rdf.repository.CandidateRepository;
import co.edu.iush.ontology.rdf.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Validated
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    private final Validator validator;

    public JobOfferService(Validator validator) {
        this.validator = validator;
    }

    // Method to list candidates by filter
    public List<JobOffer> listJobOffersByFilter(String filter) {
        List<JobOffer> listJobOffers = jobOfferRepository.findAll();
        return listJobOffers;
    }

    // Method to update a candidate
    public JobOffer updateJobOffer(Long jobOfferId, JobOffer updatedJobOffer) throws Exception {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Job Offer not found with id: " + jobOfferId));
        validateProperties(updatedJobOffer);
        updateEditableProperties(jobOffer, updatedJobOffer);

        return jobOfferRepository.save(jobOffer);
    }

    // Method to create a candidate
    public JobOffer createJobOffer(JobOffer jobOffer) throws Exception {
        validateProperties(jobOffer);
        jobOffer.setCreationDate(new Date());
        return jobOfferRepository.save(jobOffer);
    }

    // Method to delete a candidate
    public void deleteJobOffer(Long jobOfferId) {
        // Check if the candidate exists
        if (!jobOfferRepository.existsById(jobOfferId)) {
            throw new RuntimeException("Candidate not found with id: " + jobOfferId);
        }
        jobOfferRepository.deleteById(jobOfferId);
    }

    /**
     * Updates the editable properties of the given job offer with the values from the updated job offer.
     * Only the editable properties, such as name, remote and salary are updated.
     *
     * @param jobOffer        The Job Offer to be updated.
     * @param jobOfferUpdated The Job Offer containing the updated values.
     */
    private void updateEditableProperties(JobOffer jobOffer, JobOffer jobOfferUpdated) {
        jobOffer.setOccupationName(jobOfferUpdated.getOccupationName());
        jobOffer.setCreationDate(new Date());
        jobOffer.setRemote(jobOfferUpdated.getRemote());
        jobOffer.setSalary(jobOfferUpdated.getSalary());
    }

    /*
        Validate properties
     */
    private void validateProperties(JobOffer jobOffer) throws ERecruitmentException {
        Errors errors = new BeanPropertyBindingResult(jobOffer, "JobOffer");
        validator.validate(jobOffer, errors);

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
