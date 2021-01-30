package hranj.marijan.springbootapp.services.impl;

import hranj.marijan.springbootapp.dto.CityDto;
import hranj.marijan.springbootapp.model.City;
import hranj.marijan.springbootapp.repository.CityRepository;
import hranj.marijan.springbootapp.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void addNewCity(CityDto cityDto) {
        cityRepository.save(new City(cityDto));
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public Optional<City> findById(Integer id) {
        return cityRepository.findById(id);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> findAllSortedByTimeAdded() {
        return cityRepository.findByOrderByTimeAddedAsc();
    }

    @Override
    public List<City> findMostPopularCities() {
        return cityRepository.findMostPopularCities();
    }

}
