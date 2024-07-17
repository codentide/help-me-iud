package co.edu.iudigital.helpmeiud.controllers;

import co.edu.iudigital.helpmeiud.dtos.users.ProfileRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.users.ProfileResponseDTO;
import co.edu.iudigital.helpmeiud.dtos.users.UserRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.users.UserResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "User Controller", description = "Controlador para gestión de usuarios")
@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // GET USER BY ID
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Get a User By ID - (ADMIN)", description = "Endpoint to get a User by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponseDTO> readUserById(@PathVariable Long id) throws RestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.readUserById(id));
    }

    // CREATE USER
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "User Signup", description = "Endpoint to Signup as User")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO reqDto) throws RestException {
        final String passwordEncoded = passwordEncoder.encode(reqDto.getPassword());
        reqDto.setPassword(passwordEncoded);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(reqDto));
    }

    // GET PROFILE
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Read Profile - (USER)", description = "Endpoint to read get an authenticated user profile")
    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDTO> readUserProfile(Authentication auth) throws RestException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.readUserByUsername(auth.getName()));
    }

    // UPDATE PROFILE
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Error Server")
    })
    @Operation(summary = "User Update - (USER)", description = "Endpoint to update an authenticated User")
    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProfileResponseDTO> updateUserProfile(@RequestBody ProfileRequestDTO request, Authentication auth) throws RestException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.updateUser(request, auth));
    }

    // CHANGE PROFILE IMG
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Error Server")
    })
    @Operation(summary = "Change Profile Image - (USER)", description = "Endpoint to update a user profile img")
    @PatchMapping("/profile/img")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDTO> updateUserImage(@RequestParam("img") MultipartFile image, Authentication auth) throws RestException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.updateUserImage(image, auth));
    }

}
