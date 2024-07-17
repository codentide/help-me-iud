package co.edu.iudigital.helpmeiud.utils;

import co.edu.iudigital.helpmeiud.dtos.crimes.CrimeRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.crimes.CrimeResponseDTO;
import co.edu.iudigital.helpmeiud.models.Crime;
import co.edu.iudigital.helpmeiud.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CrimeMapper {

    public List<CrimeResponseDTO> toResDtoList(List<Crime> crimeEntityList){
        return crimeEntityList.stream()
                .map(this::toResDto)
                .collect(Collectors.toList());
    }

    public CrimeResponseDTO toResDto (Crime crimeEntity){
        return CrimeResponseDTO.builder()
                .id(crimeEntity.getId())
                .name(crimeEntity.getName())
                .description(crimeEntity.getDescription())
                .user(crimeEntity.getUser().getUsername())
                .createdAt(crimeEntity.getCreatedAt())
                .updatedAt(crimeEntity.getUpdatedAt())
                .build();
    }

    public Crime toEntity (CrimeRequestDTO requestDTO, User userDB){

        Crime crimeEntity = new Crime();
        crimeEntity.setName(requestDTO.getName());
        crimeEntity.setDescription(requestDTO.getDescription());
        crimeEntity.setUser(userDB);

        return crimeEntity;

    }
}
