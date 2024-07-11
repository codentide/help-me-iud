package co.edu.iudigital.helpmeiud.services.impl;

import co.edu.iudigital.helpmeiud.exceptions.ErrorDTO;
import co.edu.iudigital.helpmeiud.exceptions.InternalServerException;
import co.edu.iudigital.helpmeiud.exceptions.NotFoundException;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.Crime;
import co.edu.iudigital.helpmeiud.repositories.ICrimeRepository;
import co.edu.iudigital.helpmeiud.services.interfaces.ICrimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CrimeServiceImpl implements ICrimeService {

    @Autowired
    private ICrimeRepository crimeRepository;

    @Override
    public Crime createCrime(Crime crime) throws RestException {
        log.info("[CrimeService] Create Crime - {}", crime.getName());
        try {
            return crimeRepository.save(crime);
        } catch (Exception e) {
            log.error("Error creating crime: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .error("Internal Server Error")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(e.getMessage())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Crime readCrime(Long id) throws RestException {
        log.info("[CrimeService] Read Crime - ID: {}", id);
        return crimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorDTO.builder()
                                .error("Not Found")
                                .message("Crime not found with ID " + id)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()
                ));
    }

    @Override
    public List<Crime> readCrimes() throws RestException {
        log.info("[CrimeService] Get all Crimes");
        try {
            return crimeRepository.findAll();
        } catch (Exception e) {
            log.error("Error retrieving all crimes: {}", e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .error("Internal Server Error")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(e.getMessage())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

    @Transactional
    @Override
    public Crime updateCrime(Long id, Crime crime) throws RestException {
        log.info("[CrimeService] Update Crime - ID: {}", id);

        Crime crimeBD = crimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorDTO.builder()
                                .error("Not Found")
                                .message("Crime not found with ID " + id)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()
                ));

        if (crime.getName() != null && !crime.getName().isEmpty()) {
            crimeBD.setName(crime.getName());
        }

        if (crime.getDescription() != null && !crime.getDescription().isEmpty()) {
            crimeBD.setDescription(crime.getDescription());
        }

        try {
            return crimeRepository.save(crimeBD);
        } catch (Exception e) {
            log.error("Error updating crime - ID: {}: {}", id, e.getMessage());
            throw new InternalServerException(
                    ErrorDTO.builder()
                            .error("Internal Server Error")
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(e.getMessage())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }


    @Transactional
    @Override
    public void deleteCrime(Long id) throws RestException {
        log.info("[CrimeService] Delete Crime - ID: {}", id);
        try {
            crimeRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting crime - ID: {}: {}", id, e.getMessage());
            throw new NotFoundException(
                    ErrorDTO.builder()
                            .error("Not Found")
                            .message("Crime not found with ID " + id)
                            .status(HttpStatus.NOT_FOUND.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }
}
