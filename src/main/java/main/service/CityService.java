package main.service;

import main.entity.City;
import main.entity.CityDTO;
import main.entity.mappers.CityMapper;
import main.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public CityDTO getCityByName(String name) {
        return cityMapper.cityToDTO(cityRepository.findByCity(name));
    }

    public City getCity(Double lat, Double lon) {
        return cityRepository.findByLatAndLon(lat, lon);
    }
}
