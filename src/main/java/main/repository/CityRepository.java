package main.repository;

import main.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {

    @Override
    List<City> findAll();

    City findByLatAndLon(Double lat, Double lon);
}
