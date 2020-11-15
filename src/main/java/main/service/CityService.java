package main.service;

import main.entity.City;
import main.entity.CityDTO;
import main.entity.mappers.CityMapper;
import main.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public List<CityDTO> getCityByName(String name) {
        List<City> cities = cityRepository.findAllByCityContainingIgnoreCase(name);
        List<CityDTO> cityDTOS = new ArrayList<>();
        for(City city : cities){
            cityDTOS.add(cityMapper.cityToDTO(city));
        }
        return cityDTOS;
    }

    public City getCity(Double lat, Double lon) {
        return cityRepository.findByLatAndLon(lat, lon);
    }
}
