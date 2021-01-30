package hranj.marijan.springbootapp.services;

import hranj.marijan.springbootapp.dto.CityDto;
import hranj.marijan.springbootapp.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    void addNewCity(CityDto cityDto);

    void saveCity(City city);

    Optional<City> findById(Integer id);

    List<City> findAll();

    List<City> findAllSortedByTimeAdded();

    List<City> findMostPopularCities();

}
