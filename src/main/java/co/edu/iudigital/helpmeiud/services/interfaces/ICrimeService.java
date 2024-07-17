package co.edu.iudigital.helpmeiud.services.interfaces;

import co.edu.iudigital.helpmeiud.dtos.crimes.CrimeRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.crimes.CrimeResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICrimeService {

    CrimeResponseDTO createCrime(CrimeRequestDTO requestDTO, Authentication auth);
    CrimeResponseDTO readCrimeById(Long id);
    List<CrimeResponseDTO> readAllCrimes();
    CrimeResponseDTO updateCrime(Long id, CrimeRequestDTO requestDTO);
    CrimeResponseDTO deleteCrime(Long id);
}
