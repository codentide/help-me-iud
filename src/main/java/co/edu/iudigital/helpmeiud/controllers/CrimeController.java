package co.edu.iudigital.helpmeiud.controllers;

import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.Crime;
import co.edu.iudigital.helpmeiud.services.interfaces.ICrimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/crimes")
public class CrimeController {

    @Autowired
    private ICrimeService iCrimeService;

    // Create
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Save Crime", description = "Endpoint to save a Crime")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Crime> save(@Valid @RequestBody Crime crime) throws RestException{
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(iCrimeService.createCrime(crime));
    }

    // Read
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Read Crime", description = "Endpoint to read/get a Crime by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Crime> read(@PathVariable(value = "id") Long id) throws RestException{
        return ResponseEntity
                .status(HttpStatus.OK) // Cambiado a HttpStatus.OK
                .body(iCrimeService.readCrime(id));
    }

    // Read All
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Read all Crimes", description = "Endpoint to read/get all Crimes in DB")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Crime>> readAll() throws RestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCrimeService.readCrimes());
    }

    // Update
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Update Crime", description = "Endpoint to update a Crime by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Crime> update( @RequestBody Crime crime, @PathVariable(value = "id") Long id) throws RestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCrimeService.updateCrime(id, crime));
    }

    // Delete
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(summary = "Delete Crime", description = "Endpoint to delete a Crime by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) throws RestException{
        iCrimeService.deleteCrime(id);
    }


}
