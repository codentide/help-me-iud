package co.edu.iudigital.helpmeiud.services.interfaces;

import co.edu.iudigital.helpmeiud.dtos.users.ProfileRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.users.ProfileResponseDTO;
import co.edu.iudigital.helpmeiud.dtos.users.UserRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.users.UserResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    List<UserResponseDTO> readUsers() throws RestException;
    UserResponseDTO readUserById(Long id) throws RestException;
    UserResponseDTO readUserByUsername(String username) throws RestException;
    UserResponseDTO registerUser(UserRequestDTO request) throws RestException;
    ProfileResponseDTO updateUser(ProfileRequestDTO request, Authentication auth) throws RestException;
    UserResponseDTO updateUserImage(MultipartFile img, Authentication auth) throws RestException;

}

