package hranj.marijan.springbootapp.repository;

import hranj.marijan.springbootapp.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    String MOST_POPULAR_CITIES_QUERY = "SELECT C.* FROM CITY C INNER JOIN FAVOURITE_CITY FAV_C " +
            "on C.ID=FAV_C.FK_CITY GROUP BY NAME ORDER BY count(name) DESC";

    List<City> findByOrderByTimeAddedAsc();

    @Query(value = MOST_POPULAR_CITIES_QUERY,
            nativeQuery = true)
    List<City> findMostPopularCities();

}
