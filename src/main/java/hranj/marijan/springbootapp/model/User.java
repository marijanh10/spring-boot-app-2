package hranj.marijan.springbootapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 45)
    @Basic
    private String email;

    @Column(nullable = false, length = 100)
    @Basic
    private String password;

    @ManyToMany
    @JoinTable(name = "favourite_city",
            joinColumns = { @JoinColumn(name = "fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_city") }
    )
    private Set<City> myFavouriteCities = new HashSet<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {}

    public void addFavouriteCity(City city) {
        myFavouriteCities.add(city);
        city.getUsersWhoMarkedAsFavourite().add(this);
    }

    public void removeFavouriteCity(City city) {
        myFavouriteCities.remove(city);
        city.getUsersWhoMarkedAsFavourite().remove(this);
    }

}
