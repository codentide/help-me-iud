package co.edu.iudigital.helpmeiud.services.interfaces;

import co.edu.iudigital.helpmeiud.models.Crime;

import java.util.List;

public interface ICrimeService {

    Crime createCrime(Crime crime);
    Crime readCrime(Long id);
    List<Crime> readCrimes();
    Crime updateCrime(Long id, Crime crime);
    void deleteCrime(Long id);
}
