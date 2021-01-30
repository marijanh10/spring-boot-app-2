package hranj.marijan.springbootapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hranj.marijan.springbootapp.dto.CityDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class City {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 45)
    @Basic
    private String name;

    @Column(nullable = false, length = 255)
    @Basic
    private String description;

    @Column(nullable = false)
    @Basic
    private Long population;

    @Column(nullable = false)
    @Basic
    private Timestamp timeAdded;

    @ManyToMany(mappedBy = "myFavouriteCities")
    @JsonIgnore
    private Set<User> usersWhoMarkedAsFavourite = new HashSet<>();

    public City(CityDto cityDto) {
        this.name = cityDto.getName();
        this.description = cityDto.getDescription();
        this.population = cityDto.getPopulation();
        this.timeAdded = new Timestamp(System.currentTimeMillis());
    }

    public City(String name, String description, Long population, Timestamp timestamp) {
        this.name = name;
        this.description = description;
        this.population = population;
        this.timeAdded = timestamp;
    }

    public int getFavouriteCount() {
        return usersWhoMarkedAsFavourite.size();
    }

    public City() {}

}
