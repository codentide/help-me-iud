package co.edu.iudigital.helpmeiud.services.impl;

import co.edu.iudigital.helpmeiud.dtos.cases.CaseRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.cases.CaseResponseDTO;
import co.edu.iudigital.helpmeiud.dtos.cases.CaseVisibilityResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.*;
import co.edu.iudigital.helpmeiud.models.Case;
import co.edu.iudigital.helpmeiud.models.Crime;
import co.edu.iudigital.helpmeiud.models.User;
import co.edu.iudigital.helpmeiud.repositories.ICaseRepository;
import co.edu.iudigital.helpmeiud.repositories.ICrimeRepository;
import co.edu.iudigital.helpmeiud.repositories.IUserRepository;
import co.edu.iudigital.helpmeiud.services.interfaces.ICaseService;
import co.edu.iudigital.helpmeiud.utils.CaseMapper;
import co.edu.iudigital.helpmeiud.utils.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CaseServiceImpl implements ICaseService {

    @Autowired
    private CaseMapper caseMapper;

    @Autowired
    private ICaseRepository iCaseRepository;

    @Autowired
    private ICrimeRepository iCrimeRepository;

    @Autowired
    private IUserRepository userRepository;

    // CREATE CASE
    @Override
    public CaseResponseDTO createCase(CaseRequestDTO request, Authentication auth) throws RestException {
        log.info("[createCase({})] - Creating Case" , request.getDescription());
        // Validación del crimen
        Crime crimeDB = iCrimeRepository.findByNameIgnoreCase(request.getCrimeName());
        if (crimeDB == null) {
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .message(Messages.CRIME_NOT_FOUND)
                            .build());
        }

        // Trayendo usuario del auth
        String username = auth.getName();
        User userDB = userRepository.findByUsername(username);
        if (userDB == null) {
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .message(Messages.USER_NOT_FOUND)
                            .build());
        }
        try{
            Case caseEntity = caseMapper.toCaseEntity(request, crimeDB, userDB);
            caseEntity = iCaseRepository.save(caseEntity);
            return caseMapper.toCaseResponseDTO(caseEntity);
        } catch (Exception e) {
            log.error("Error creating case: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    // READALL
    @Override
    public List<CaseResponseDTO> readCases() throws RestException {
        log.info("[readCases()] - Reading Cases");
        try {
            // Obtener todos los casos en una lista
            List<Case> cases = iCaseRepository.findAll();
            // Mapear y retornar la lista en forma de Case DTO
            return caseMapper.toCaseResponseDTOList(cases);
        } catch (Exception e) {
            log.error("Error reading cases: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .error("Error reading cases: " + e.getMessage())
                            .build()
            );
        }
    }

    // READALL CASES BY USERNAME
    @Override
    public List<CaseResponseDTO> readCasesByUsername(String username) throws RestException {
        User userDB = userRepository.findByUsername(username);
        if(userDB == null) {
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .message("Usuario de username: "+username+" no encontrado")
                            .build());
        }
        List<Case> cases = iCaseRepository.findAllByUserIdUsername(username);
        return caseMapper.toCaseResponseDTOList(cases);
    }

    // READ ALL VISIBLE
    @Override
    public List<CaseResponseDTO> readVisibleCases() throws RestException {
        log.info("[readVisibleCases()] - Reading Visible Cases");
        try {
            // Obtener todos los casos visibles en una lista
            List<Case> cases = iCaseRepository.findAllByIsVisibleTrue();
            // Mapear y retornar la lista en forma de Case DTO
            return caseMapper.toCaseResponseDTOList(cases);
        } catch (Exception e) {
            log.error("Error reading cases: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .error("Error reading cases: " + e.getMessage())
                            .build()
            );
        }
    }
    
    @Override
    public CaseResponseDTO readCase(Long id) throws RestException {
        log.info("[readCase()] - Reading Case");
        Case caseDB = iCaseRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorDTO.builder()
                                        .message("Caso no encontrado")
                                        .build())
                );
        return caseMapper.toCaseResponseDTO(caseDB);
    }

    @Override
    public CaseResponseDTO updateCase(CaseRequestDTO caseRequestDTO, Long id, Authentication auth) throws RestException {

        User userDB = userRepository.findByUsername(auth.getName());
        if (userDB == null) {
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .message(Messages.USER_NOT_FOUND)
                            .build());
        }

        Case caseDB = iCaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorDTO.builder()
                                .message(Messages.CASE_NOT_FOUND)
                                .build()
                ));

        if (!auth.getName().equals(caseDB.getUserId().getUsername())) {
            throw new UnauthorizedException(ErrorDTO.builder()
                    .message("No tiene permiso para borrar este caso")
                    .build());
        }

        // Latitude
        if (caseRequestDTO.getLatitude() != null) {
            caseDB.setLatitude(caseRequestDTO.getLatitude());
        }
        // Longitude
        if (caseRequestDTO.getLongitude() != null) {
            caseDB.setLongitude(caseRequestDTO.getLongitude());
        }
        // Altitude
        if (caseRequestDTO.getAltitude() != null) {
            caseDB.setAltitude(caseRequestDTO.getAltitude());
        }
        // Description
        if (caseRequestDTO.getDescription() != null && !caseRequestDTO.getDescription().isEmpty()) {
            caseDB.setDescription(caseRequestDTO.getDescription());
        }
        // Rmi Url
        if (caseRequestDTO.getRmiUrl() != null && !caseRequestDTO.getRmiUrl().isEmpty()) {
            caseDB.setRmiUrl(caseRequestDTO.getRmiUrl());
        }
        // Map Url
        if (caseRequestDTO.getMapUrl() != null && !caseRequestDTO.getMapUrl().isEmpty()) {
            caseDB.setMapUrl(caseRequestDTO.getMapUrl());
        }
        // Date Time
        if (caseRequestDTO.getDateTime() != null) {
            caseDB.setDateTime(caseRequestDTO.getDateTime());
        }
        // Crime
        if (caseRequestDTO.getCrimeName() != null) {
            Crime crimeDB = iCrimeRepository.findByNameIgnoreCase(caseRequestDTO.getCrimeName());
            if (crimeDB == null) {
                throw new NotFoundException(
                        ErrorDTO.builder()
                                .message(Messages.CRIME_NOT_FOUND)
                                .build());
            }
            caseDB.setCrimeId(crimeDB);
        }
        try {
            caseDB = iCaseRepository.save(caseDB);
            return caseMapper.toCaseResponseDTO(caseDB);
        } catch (Exception e) {
            log.error("Error updating crime - ID: {}: {}", id, e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .error("Internal Server Error")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build()
            );
        }
    }

    @Override
    public CaseResponseDTO deleteCase(Long id, Authentication auth) throws RestException {

        log.info("[CaseService] Delete Case - ID: {}", id);
        // Trayendo usuario del auth
        User userDB = userRepository.findByUsername(auth.getName());
        if (userDB == null) {
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .message(Messages.USER_NOT_FOUND)
                            .build());
        }

        Case caseDB = iCaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorDTO.builder()
                                .error("Not Found")
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(Messages.CASE_NOT_FOUND)
                                .build()
                ));

        if (!auth.getName().equals(caseDB.getUserId().getUsername())) {
            throw new UnauthorizedException(ErrorDTO.builder()
                    .message("No tiene permiso para borrar este caso")
                    .build());
        }
        iCaseRepository.deleteById(id);
        return caseMapper.toCaseResponseDTO(caseDB);
    }

    /*

        log.info("[CrimeService] Delete Crime - ID: {}", id);
        try {
            Crime crimeDB = crimeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(
                            ErrorDTO.builder()
                                    .error("Not Found")
                                    .status(HttpStatus.NOT_FOUND.value())
                                    .message("El recurso solicitado con ID ("+id+") no se encontró en el sistema.")
                                    .build()
                    ));
            crimeRepository.deleteById(id);
            return crimeMapper.toResDto(crimeDB);
        } catch (Exception e) {
            log.error("Error deleting crime - ID: {}: {}", id, e.getMessage());
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .error("Not Found")
                            .message("El recurso solicitado con ID ("+id+") no se encontró en el sistema.")
                            .status(HttpStatus.NOT_FOUND.value())
                            .build()
            );
        }
     */

    @Override
    public CaseVisibilityResponseDTO changeVisibility(Boolean visibility, Long id) throws RestException {
        log.info("[changeVisibility({})] - Changing Visibility", visibility);
        Case caseDB = iCaseRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorDTO.builder()
                                        .message(Messages.CASE_NOT_FOUND)
                                        .build())
                );

        CaseVisibilityResponseDTO visibilityResponse = new CaseVisibilityResponseDTO();
        visibilityResponse.setId(caseDB.getId());
        visibilityResponse.setIsVisible(visibility);

        try {
            caseDB.setIsVisible(visibility);
            iCaseRepository.saveAndFlush(caseDB);
            return visibilityResponse;
        } catch (Exception e) {
            log.error("Error changing visibility: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .message("Error changing visibility: " + e.getMessage())
                            .build()
            );
        }
    }

}

