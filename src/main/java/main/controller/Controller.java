package main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.entity.*;
import main.service.Service;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @PostMapping(value = "/auth", consumes = "application/json", produces = "application/json")
    public void auth(@RequestBody RequestAuth requestAuth, HttpServletResponse httpServletResponse) {
        service.auth(requestAuth, httpServletResponse);
    }

    @GetMapping(value = "/logout")
    public void logout(@RequestParam String login, HttpServletResponse httpServletResponse) {
        service.logout(login, httpServletResponse);
    }

    @PostMapping(value = "/day", consumes = "application/json", produces = "application/json")
    public ResponseWeather getDayWeather(RestTemplate restTemplate, @RequestBody RequestWeather requestWeather) throws JsonProcessingException {
        return service.getDayWeather(restTemplate, requestWeather);
    }

    @PostMapping(value = "/week", consumes = "application/json", produces = "application/json")
    public List<ResponseWeatherWeek> getWeekWeather(RestTemplate restTemplate, @RequestBody RequestWeather requestWeather) throws JsonProcessingException {
        return service.getWeekWeather(restTemplate, requestWeather);
    }

    @GetMapping("/cities")
    public List<City> getCities() {
        return service.getCities();
    }
}
