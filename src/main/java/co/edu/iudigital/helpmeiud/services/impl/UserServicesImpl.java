package co.edu.iudigital.helpmeiud.services.impl;

import co.edu.iudigital.helpmeiud.dtos.users.ProfileRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.users.ProfileResponseDTO;
import co.edu.iudigital.helpmeiud.dtos.users.UserRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.users.UserResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.*;
import co.edu.iudigital.helpmeiud.models.Role;
import co.edu.iudigital.helpmeiud.models.User;
import co.edu.iudigital.helpmeiud.repositories.IRoleRepository;
import co.edu.iudigital.helpmeiud.repositories.IUserRepository;
import co.edu.iudigital.helpmeiud.services.interfaces.IEmailService;
import co.edu.iudigital.helpmeiud.services.interfaces.IUserService;
import co.edu.iudigital.helpmeiud.utils.Messages;
import co.edu.iudigital.helpmeiud.utils.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServicesImpl implements IUserService, UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IEmailService emailService;

    @Override
    public List<UserResponseDTO> readUsers() throws RestException {
        return null;
    }

    @Override
    public UserResponseDTO readUserById(Long id) throws RestException {
        log.info("Reading user by id: {}", id);
        User userDB = userRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                ErrorDTO.builder()
                                        .message("Usuario no encontrado")
                                        .build()
                        )
                );

        return userMapper.toResDto(userDB);
    }

    @Override
    public UserResponseDTO readUserByUsername(String username) throws RestException {
        log.info("Reading user by username: {}", username);
        User userDB = userRepository.findByUsername(username);
        if(userDB == null){
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .message(Messages.USER_NOT_FOUND)
                            .build());
        }

        return userMapper.toResDto(userDB);
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO reqDto) throws RestException {
        User userDB = userRepository.findByUsername(reqDto.getUsername());
        if(userDB != null){
            throw new BadRequestException(
                    ErrorDTO.builder()
                            .message(Messages.USER_ALREADY_EXISTS)
                            .build());
        }
        try{
            // Registry
            User userEntity = userMapper.toEntity(reqDto);
            userEntity.setEnabled(true);
            userEntity.setRoles(Collections.singletonList(new Role(2L)));
            userEntity = userRepository.save(userEntity);

            // Email Send
            String username = userEntity.getUsername();
            String message = "Hello " + userEntity.getName() + ", Account created successfully " + username;
            String subject = "HelpMeiud - " + username;
            boolean sent = emailService.sendMail(message,username,subject);

            if (sent) {
                log.info("[registerUser] - Email sent successfully");
            } else {
                log.warn("[registerUser] - Email not sent");
            }

            return userMapper.toResDto(userEntity);
        } catch (Exception e) {
            log.error("[registerUser] - Error creating user: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @Override
    public ProfileResponseDTO updateUser(ProfileRequestDTO request, Authentication auth) throws RestException {

        // Validar existencia del usuario
        User userDB = userRepository.findByUsername(auth.getName());
        if(userDB == null){
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .message(Messages.USER_NOT_FOUND)
                            .build());
        }

        // Validar request para sobreescribir
        if (request.getName() != null){
            userDB.setName(request.getName());
        }
        if (request.getLastName() != null){
            userDB.setLastName(request.getLastName());
        }
        if (request.getSocialWeb() != null){
            userDB.setSocialWeb(request.getSocialWeb());
        }
        if (request.getBirthDate() != null) {
            userDB.setBirthDate(request.getBirthDate());
        }

        try {
            // Guardar Usuario
            userDB = userRepository.save(userDB);
            return userMapper.toProfileResDto(userDB);
        } catch (Exception e) {
            log.error("[updateProfile] - Error updating user: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    // SUBIR FOTO DE PERFIL
    @Override
    public UserResponseDTO updateUserImage(MultipartFile image, Authentication authentication) throws RestException {
        User usuario = userRepository.findByUsername(authentication.getName());
        if(!image.isEmpty()) {

            String nombreImage = UUID
                    .randomUUID()
                    .toString()
                    .concat("_")
                    .concat(image.getOriginalFilename())
                    .replace(" ", "");
            log.info("[updateUserImage] - fileName: {}",nombreImage);
            Path path = Paths.get("uploads").resolve(nombreImage).toAbsolutePath();
            log.info("[updateUserImage] - Path: {}", path);
            try {
                //Files.copy(image.getInputStream(), path);
                Files.copy(image.getInputStream(), path);

            } catch (IOException e) {
                log.error(e.getMessage(), e.getCause());
                throw new BadRequestException(
                        ErrorDTO.builder()
                                .message(e.getMessage())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Error de imagen")
                                .build()
                );
            }

            String imageAnterior = usuario.getImage();

            if(imageAnterior != null && imageAnterior.length() > 0 && !imageAnterior.startsWith("nombreImage")){
                Path pathAnterior = Paths.get("uploads").resolve(imageAnterior).toAbsolutePath();
                File fileAnterior = pathAnterior.toFile();
                if(fileAnterior.exists() && fileAnterior.canRead()) {
                    fileAnterior.delete();
                }
            }

            usuario.setImage(nombreImage);
            userRepository.save(usuario);
        }
        return userMapper.toResDto(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        log.warn(authorities.toString());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

}

