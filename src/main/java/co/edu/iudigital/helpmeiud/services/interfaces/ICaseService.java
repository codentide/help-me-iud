package co.edu.iudigital.helpmeiud.services.interfaces;

import co.edu.iudigital.helpmeiud.dtos.cases.CaseRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.cases.CaseResponseDTO;
import co.edu.iudigital.helpmeiud.dtos.cases.CaseVisibilityResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICaseService {

    List<CaseResponseDTO> readCases() throws RestException;
    List<CaseResponseDTO> readVisibleCases() throws RestException;
    List<CaseResponseDTO> readCasesByUsername(String username) throws RestException;
    CaseResponseDTO readCase(Long id) throws RestException;
    CaseResponseDTO createCase(CaseRequestDTO caseRequestDTO, Authentication auth) throws RestException;
    CaseResponseDTO updateCase(CaseRequestDTO caseRequestDTO, Long id, Authentication auth) throws RestException;
    CaseResponseDTO deleteCase(Long id, Authentication auth) throws RestException;
    CaseVisibilityResponseDTO changeVisibility(Boolean visibility, Long id) throws RestException;

}
