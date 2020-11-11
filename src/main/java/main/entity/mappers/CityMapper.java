package main.entity.mappers;

import main.entity.City;
import main.entity.CityDTO;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {


    public CityDTO cityToDTO(City city) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setCity(city.getCity());
        cityDTO.setLat(city.getLat());
        cityDTO.setLon(city.getLon());
        return cityDTO;
    }
}
