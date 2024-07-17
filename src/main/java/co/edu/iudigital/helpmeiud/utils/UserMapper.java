package co.edu.iudigital.helpmeiud.utils;

import co.edu.iudigital.helpmeiud.dtos.users.ProfileResponseDTO;
import co.edu.iudigital.helpmeiud.dtos.users.UserRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.users.UserResponseDTO;
import co.edu.iudigital.helpmeiud.models.Role;
import co.edu.iudigital.helpmeiud.models.User;
import co.edu.iudigital.helpmeiud.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    IUserService userService;

    public ProfileResponseDTO toProfileResDto(User userEntity){
        return ProfileResponseDTO.builder()
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .birthDate(userEntity.getBirthDate())
                .socialWeb(userEntity.getSocialWeb())
                .roleNames(getRolesNames(userEntity.getRoles()))
                .build();
    }

    // EntityToDto

    public UserResponseDTO toResDto(User userEntity) {
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .birthDate(userEntity.getBirthDate())
                .image(userEntity.getImage())
                .enabled(userEntity.getEnabled())
                .socialWeb(userEntity.getSocialWeb())
                .roles(getRolesNames(userEntity.getRoles()))
                .build();
    }

    public UserRequestDTO toReqDto(User userEntity) {
        return UserRequestDTO.builder()
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .birthDate(userEntity.getBirthDate())
                .image(userEntity.getImage())
                .enabled(userEntity.getEnabled())
                .socialWeb(userEntity.getSocialWeb())
                .build();
    }

    // DtoToEntity
    public User toEntity(UserRequestDTO userRequestDTO) {
        User caseEntity = new User();
        caseEntity.setName(userRequestDTO.getName());
        caseEntity.setLastName(userRequestDTO.getLastName());
        caseEntity.setUsername(userRequestDTO.getUsername());
        caseEntity.setPassword(userRequestDTO.getPassword());
        caseEntity.setBirthDate(userRequestDTO.getBirthDate());
        caseEntity.setImage(userRequestDTO.getImage());
        caseEntity.setEnabled(userRequestDTO.getEnabled());
        caseEntity.setSocialWeb(userRequestDTO.getSocialWeb());
        return caseEntity;
    }

    public User toEntity(UserResponseDTO userResponseDTO) {
        User caseEntity = new User();
        caseEntity.setId(userResponseDTO.getId());
        caseEntity.setName(userResponseDTO.getName());
        caseEntity.setLastName(userResponseDTO.getLastName());
        caseEntity.setUsername(userResponseDTO.getUsername());
        caseEntity.setPassword(userResponseDTO.getPassword());
        caseEntity.setBirthDate(userResponseDTO.getBirthDate());
        caseEntity.setImage(userResponseDTO.getImage());
        caseEntity.setEnabled(userResponseDTO.getEnabled());
        caseEntity.setSocialWeb(userResponseDTO.getSocialWeb());
        return caseEntity;
    }

    // Helpers
    public List<String> getRolesNames(List<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<Long> getRolesIds(List<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }
}

/*
    public List<CaseResponseDTO> toCaseResponseDTOList(List<Case> cases) {
        return cases.stream()
                .map(caseEntity -> toCaseResponseDTO(caseEntity))
                .collect(Collectors.toList());
    }
 */