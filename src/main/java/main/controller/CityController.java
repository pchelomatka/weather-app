package main.controller;

import main.entity.CityDTO;
import main.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city")
    public CityDTO getCities(@RequestParam String name) {
        return cityService.getCityByName(name);
    }
}
