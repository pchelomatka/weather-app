package main.repository;

import main.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {

    List<City> findAllByCityContainingIgnoreCase(String name);

    City findByLatAndLon(Double lat, Double lon);
}
