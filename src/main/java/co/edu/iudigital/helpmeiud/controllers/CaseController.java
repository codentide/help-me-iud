package co.edu.iudigital.helpmeiud.controllers;

import co.edu.iudigital.helpmeiud.dtos.cases.CaseRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.cases.CaseResponseDTO;
import co.edu.iudigital.helpmeiud.dtos.cases.CaseVisibilityRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.cases.CaseVisibilityResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.ErrorDTO;
import co.edu.iudigital.helpmeiud.exceptions.ForbiddenException;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.services.interfaces.ICaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Cases Controller", description = "Controlador para gestión de casos")
@RestController
@RequestMapping("/cases")
@Slf4j
public class CaseController {

    @Autowired
    ICaseService iCaseService;

    // CREATE CASE
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Create a Case - (USER)", description = "Endpoint to create a case")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<CaseResponseDTO> createCase( @RequestBody CaseRequestDTO request, Authentication auth) throws RestException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(iCaseService.createCase(request, auth));
    }

    // READ CASE BY ID
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Get a Case", description = "Endpoint to get a Case")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CaseResponseDTO> read(@PathVariable Long id) throws RestException {
        return ResponseEntity.status(HttpStatus.OK).body(iCaseService.readCase(id));
    }

    // READ ALL CASES
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Get All Cases", description = "Endpoint to get all Cases")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CaseResponseDTO>> readAll() throws RestException {
        return ResponseEntity.status(HttpStatus.OK).body(iCaseService.readCases());
    }

    // READ ALL CASES BY USERNAME
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Get All Cases of User by username - (USER)", description = "Endpoint to get all Cases of a user")
    @GetMapping("username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CaseResponseDTO>> readAllByUsername(@PathVariable String username) throws RestException {
        return ResponseEntity.status(HttpStatus.OK).body(iCaseService.readCasesByUsername(username));
    }

    // READ ALL VISIBLES CASES
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Get All Visible Cases - (ADMIN)", description = "Endpoint to get all visible Cases")
    @GetMapping("/visibles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CaseResponseDTO>> readAllVisibles() throws RestException {
        return ResponseEntity.status(HttpStatus.OK).body(iCaseService.readVisibleCases());
    }

    // UPDATE CASE
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Update a Case - (USER)", description = "Endpoint to update a case")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public ResponseEntity<CaseResponseDTO> updateCase(@PathVariable Long id, @RequestBody CaseRequestDTO caseRequestDTO, Authentication auth) throws RestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCaseService.updateCase(caseRequestDTO, id, auth));
    }

    // CHANGE CASE VISIBILITY BY ID
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "Authorization")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Change a Case Visibility - (ADMIN)", description = "Endpoint change a case visibility (true = visible, false = notVisible)")
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/visibility/{id}")
    public ResponseEntity<CaseVisibilityResponseDTO> changeVisibility(@PathVariable Long id, @Valid @RequestBody CaseVisibilityRequestDTO visibilityRequest) throws RestException {
        try {
            Boolean visibility = visibilityRequest.getIsVisible();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(iCaseService.changeVisibility(visibility, id));
        } catch (Exception e) {
            throw new ForbiddenException(
                    ErrorDTO.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    // DELETE CASE
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Delete Case - (USER)", description = "Endpoint to delete a Case by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id, Authentication auth) throws RestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCaseService.deleteCase(id, auth));
    }

}
