package co.edu.iush.ontology.rdf.exception;

import java.util.List;
import co.edu.iush.ontology.rdf.dto.ErrorResponseDTO;

public class ERecruitmentException extends Exception {

    private List<ErrorResponseDTO> errorList;

    public List<ErrorResponseDTO> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorResponseDTO> errorList) {
        this.errorList = errorList;
    }
}
