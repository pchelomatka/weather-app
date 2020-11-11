package main.repository;

import main.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {

    City findByCity(String name);

    City findByLatAndLon(Double lat, Double lon);
}
