package co.edu.iudigital.helpmeiud.services.impl;

import co.edu.iudigital.helpmeiud.dtos.crimes.CrimeRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.crimes.CrimeResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.*;
import co.edu.iudigital.helpmeiud.models.Crime;
import co.edu.iudigital.helpmeiud.models.User;
import co.edu.iudigital.helpmeiud.repositories.ICrimeRepository;
import co.edu.iudigital.helpmeiud.repositories.IUserRepository;
import co.edu.iudigital.helpmeiud.services.interfaces.ICrimeService;
import co.edu.iudigital.helpmeiud.utils.CrimeMapper;
import co.edu.iudigital.helpmeiud.utils.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CrimeServiceImpl implements ICrimeService {

    @Autowired
    private ICrimeRepository crimeRepository;

    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private CrimeMapper crimeMapper;

    @Override
    public CrimeResponseDTO createCrime(CrimeRequestDTO requestDTO, Authentication auth) throws RestException {

        log.info("[CrimeService] Create Crime - {}", requestDTO.getName());
        String username = auth.getName();
        User userDB = userRepository.findByUsername(username);

        // Validar si existe un crimen con un nombre igual
        Crime crimeDB = crimeRepository.findByNameIgnoreCase(requestDTO.getName());
        if (crimeDB != null) {
            throw new BadRequestException(
                    ErrorDTO.builder()
                            .message(Messages.CRIME_ALREADY_EXISTS)
                            .build()
            );
        }

        try {
            Crime newCrime = crimeMapper.toEntity(requestDTO, userDB);
            newCrime = crimeRepository.save(newCrime);
            return crimeMapper.toResDto(newCrime);
        } catch (Exception e) {
            log.error("Error creating crime: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }
    
    @Transactional(readOnly = true)
    @Override
    public CrimeResponseDTO readCrimeById(Long id) throws RestException {
        log.info("[CrimeService] Read Crime - ID: {}", id);
        Crime crimeDB = crimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorDTO.builder()
                                .message(Messages.CRIME_NOT_FOUND)
                                .build()
                ));
        return crimeMapper.toResDto(crimeDB);
    }

    @Override
    public List<CrimeResponseDTO> readAllCrimes() throws RestException {
        log.info("[CrimeService] Get all Crimes");
        try {
            return crimeMapper.toResDtoList(crimeRepository.findAll());
        } catch (Exception e) {
            log.error("Error retrieving all crimes: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @Transactional
    @Override
    public CrimeResponseDTO updateCrime(Long id, CrimeRequestDTO requestDTO) throws RestException {
        log.info("[CrimeService] Update Crime - ID: {}", id);

        // Validar si existe un crimen con un nombre igual
        Crime crimeDB = crimeRepository.findByNameIgnoreCase(requestDTO.getName());
        if (crimeDB != null) {
            throw new BadRequestException(
                    ErrorDTO.builder()
                            .message(Messages.CRIME_ALREADY_EXISTS)
                            .build()
            );
        }

        // Traer el crimen por su id
        crimeDB = crimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorDTO.builder()
                                .message(Messages.CRIME_NOT_FOUND)
                                .build()
                ));

        if (requestDTO.getName() != null && !requestDTO.getName().isEmpty()) {
            crimeDB.setName(requestDTO.getName());
        }

        if (requestDTO.getDescription() != null && !requestDTO.getDescription().isEmpty()) {
            crimeDB.setDescription(requestDTO.getDescription());
        }

        try {
            crimeDB = crimeRepository.save(crimeDB);
            return crimeMapper.toResDto(crimeDB);
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


    @Transactional
    @Override
    public CrimeResponseDTO deleteCrime(Long id) throws RestException {
        log.info("[CrimeService] Delete Crime - ID: {}", id);

        // Validar si el crimen existe
        Crime crimeDB = crimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorDTO.builder()
                                .message("El recurso solicitado con ID ("+id+") no se encontró en el sistema.")
                                .build()
                ));
        try {
            crimeRepository.deleteById(id);
            return crimeMapper.toResDto(crimeDB);
        } catch (Exception e) {
            log.error("Error deleting crime - ID: {}: {}", id, e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }
}
