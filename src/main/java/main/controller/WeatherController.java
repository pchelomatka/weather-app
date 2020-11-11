package main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.entity.RequestWeather;
import main.entity.ResponseWeather;
import main.entity.ResponseWeatherWeek;
import main.service.WeatherService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @PostMapping(value = "/day", consumes = "application/json", produces = "application/json")
    public ResponseWeather getDayWeather(RestTemplate restTemplate, @RequestBody RequestWeather requestWeather) throws JsonProcessingException {
        return service.getDayWeather(restTemplate, requestWeather);
    }

    @PostMapping(value = "/week", consumes = "application/json", produces = "application/json")
    public List<ResponseWeatherWeek> getWeekWeather(RestTemplate restTemplate, @RequestBody RequestWeather requestWeather) throws JsonProcessingException {
        return service.getWeekWeather(restTemplate, requestWeather);
    }
}
