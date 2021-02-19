package au.gameofmates.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import au.gameofmates.openapi.api.CountriesApiDelegate;
import au.gameofmates.openapi.model.Country;
import au.gameofmates.repositories.CountryRepository;

@Service
public class CountriesApiDelegateImpl implements CountriesApiDelegate {


  @Autowired
  CountryRepository repository;
  
  @Override
  public ResponseEntity<List<Country>> countriesGet(String name, String isoCode) {
    

    Optional<List<Country>> lc = repository.findAll();
    
    if (lc.isPresent())
    {
      return ResponseEntity.ok().body(lc.get() );
    } else
    {
      return ResponseEntity.notFound().build();
    }
    
 
  }


}

