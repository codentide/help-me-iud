package co.edu.iudigital.helpmeiud.utils;

import co.edu.iudigital.helpmeiud.dtos.cases.CaseRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.cases.CaseResponseDTO;
import co.edu.iudigital.helpmeiud.models.Case;
import co.edu.iudigital.helpmeiud.models.Crime;
import co.edu.iudigital.helpmeiud.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CaseMapper {

    public List<CaseResponseDTO> toCaseResponseDTOList(List<Case> cases) {
        return cases.stream()
                .map(caseEntity -> toCaseResponseDTO(caseEntity))
                .collect(Collectors.toList());
    }

    public CaseResponseDTO toCaseResponseDTO(Case caseEntity) {
        return CaseResponseDTO.builder()
                        .id(caseEntity.getId())
                        .dateTime(caseEntity.getDateTime())
                        .latitude(caseEntity.getLatitude())
                        .longitude(caseEntity.getLongitude())
                        .altitude(caseEntity.getAltitude())
                        .username(caseEntity.getUserId().getUsername())
                        .crimeName(caseEntity.getCrimeId().getName())
                        .description(caseEntity.getDescription())
                        .mapUrl(caseEntity.getMapUrl())
                        .rmiUrl(caseEntity.getRmiUrl())
                        .isVisible(caseEntity.getIsVisible())
                        .build();
    }


    public Case toCaseEntity(CaseRequestDTO caseRequestDTO, Crime crimeDB, User userDB) {
        Case caseEntity = new Case();
        caseEntity.setDateTime(caseRequestDTO.getDateTime());
        caseEntity.setLatitude(caseRequestDTO.getLatitude());
        caseEntity.setLongitude(caseRequestDTO.getLongitude());
        caseEntity.setAltitude(caseRequestDTO.getAltitude());
        caseEntity.setIsVisible(true);
        caseEntity.setDescription(caseRequestDTO.getDescription());
        caseEntity.setMapUrl(caseRequestDTO.getMapUrl());
        caseEntity.setRmiUrl(caseRequestDTO.getRmiUrl());
        caseEntity.setUserId(userDB);
        caseEntity.setCrimeId(crimeDB);
        return caseEntity;
    }

}
