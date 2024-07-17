package co.edu.iudigital.helpmeiud.controllers;

import co.edu.iudigital.helpmeiud.dtos.crimes.CrimeRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.crimes.CrimeResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.services.interfaces.ICrimeService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Crime Controller", description = "Controlador para gestión de delitos")
@RestController
@RequestMapping("/crimes")
@SecurityRequirement(name = "Authorization")
public class CrimeController {

    @Autowired
    private ICrimeService iCrimeService;

    // CREATE CRIME
    @Secured("ROLE_ADMIN")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Save Crime - (ADMIN)", description = "Endpoint to save a Crime")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CrimeResponseDTO> createCrime(@RequestBody CrimeRequestDTO requestDTO, Authentication auth) throws RestException{
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(iCrimeService.createCrime(requestDTO, auth));
    }

    // READ CRIME BY ID
    @Secured("ROLE_USER")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Read Crime - (USER)", description = "Endpoint to read/get a Crime by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CrimeResponseDTO> readCrimeById(@PathVariable(value = "id") Long id) throws RestException{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCrimeService.readCrimeById(id));
    }

    // READ ALL CRIMES
    @Secured("ROLE_USER")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Read all Crimes - (USER)", description = "Endpoint to read/get all Crimes in DB")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CrimeResponseDTO>> readAllCrimes() throws RestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCrimeService.readAllCrimes());
    }

    // UPDATE CRIME
    @Secured("ROLE_ADMIN")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Update Crime - (ADMIN)", description = "Endpoint to update a Crime by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CrimeResponseDTO> updateCrime( @RequestBody CrimeRequestDTO requestDTO, @PathVariable(value = "id") Long id) throws RestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCrimeService.updateCrime(id,requestDTO));
    }

    // DELETE CRIME
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Delete Crime - (ADMIN)", description = "Endpoint to delete a Crime by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCrime(@PathVariable(value = "id") Long id) throws RestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCrimeService.deleteCrime(id));
    }


}
