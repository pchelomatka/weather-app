package main.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.entity.AdditionalParams;
import main.entity.RequestWeather;
import main.entity.ResponseWeather;
import main.entity.ResponseWeatherWeek;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class WeatherService {

    @Value("${weather.appid}")
    private String appId;
    @Value("${weather.lang}")
    private String lang;
    @Value("${weather.units}")
    private String units;
    @Value("${weather.exclude_day}")
    private String excludeDay;
    @Value("${weather.exclude_week}")
    private String excludeWeek;

    private final CityService cityService;

    public WeatherService(CityService cityService) {
        this.cityService = cityService;
    }

    private UriComponentsBuilder createUri(RequestWeather requestWeather) {
        return UriComponentsBuilder
                .fromHttpUrl("https://api.openweathermap.org/data/2.5/onecall")
                .queryParam("lat", requestWeather.getLat())
                .queryParam("lon", requestWeather.getLon())
                .queryParam("appid", appId)
                .queryParam("lang", lang)
                .queryParam("units", units);
    }

    private HttpEntity<?> createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

    public ResponseWeather getDayWeather(RestTemplate restTemplate, RequestWeather requestWeather) throws JsonProcessingException {

        ResponseWeather responseWeather = new ResponseWeather();
        AdditionalParams additionalParams = new AdditionalParams();

        String result = restTemplate.
                exchange(createUri(requestWeather)
                        .queryParam("exclude", excludeDay)
                        .toUriString(), HttpMethod.GET, createHeaders(), String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> firstMap = objectMapper.readValue(result, Map.class);
        Map<String, Object> secondMap = (Map<String, Object>) firstMap.get("current");
        List<Object> array = (List<Object>) secondMap.get("weather");
        Map<String, Object> thirdMap = (Map<String, Object>) array.get(0);
        List<Object> arrayDays = (List<Object>) firstMap.get("daily");

        responseWeather.setCity(cityService.getCity(requestWeather.getLat(), requestWeather.getLon()).getCity());

        if (requestWeather.getDay() == null) {
            if (secondMap.get("temp") instanceof Integer) {
                responseWeather.setTemperature(((Integer) secondMap.get("temp")).longValue());
            } else {
                responseWeather.setTemperature(Math.round((Double) secondMap.get("temp")));
            }
            responseWeather.setWeatherStatus(StringUtils.capitalize(thirdMap.get("description").toString()));
            responseWeather.setWeatherIcon(thirdMap.get("icon").toString().toLowerCase());
            responseWeather.setFeelingBy(Math.round((Double) secondMap.get("feels_like")));
            if (secondMap.get("feels_like") instanceof Integer) {
                responseWeather.setTemperature(((Integer) secondMap.get("feels_like")).longValue());
            } else {
                responseWeather.setTemperature(Math.round((Double) secondMap.get("feels_like")));
            }
            if (secondMap.get("wind_speed") instanceof Integer) {
                additionalParams.setWindSpeed(((Integer) secondMap.get("wind_speed")).longValue());
            } else {
                additionalParams.setWindSpeed(Math.round((Double) secondMap.get("wind_speed")));
            }
            if (secondMap.get("pressure") instanceof Integer) {
                additionalParams.setPresure(((Integer) secondMap.get("pressure")).longValue());
            } else {
                additionalParams.setPresure(Math.round((Double) secondMap.get("pressure")));
            }
            if (secondMap.get("humidity") instanceof Integer) {
                additionalParams.setHumidity(((Integer) secondMap.get("humidity")).longValue());
            } else {
                additionalParams.setHumidity(Math.round((Double) secondMap.get("humidity")));
            }
            responseWeather.setAdditionalParams(additionalParams);
            return responseWeather;
        } else {
            Long day = requestWeather.getDay().getEpochSecond();
            for (Object item : arrayDays) {
                Map<String, Object> innerMap = (Map<String, Object>) item;
                if (innerMap.get("dt").toString().equals(day.toString())) {
                    Map<String, Object> tempMap = (Map<String, Object>) innerMap.get("temp");
                    Map<String, Object> feelsLikeMap = (Map<String, Object>) innerMap.get("feels_like");
                    List<Object> tempArray = (List<Object>) innerMap.get("weather");
                    Map<String, Object> weatherMap = (Map<String, Object>) tempArray.get(0);
                    if (tempMap.get("day") instanceof Integer) {
                        responseWeather.setTemperature(((Integer) tempMap.get("day")).longValue());
                    } else {
                        responseWeather.setTemperature(Math.round((Double) tempMap.get("day")));
                    }
                    responseWeather.setWeatherStatus(StringUtils.capitalize(weatherMap.get("description").toString()));
                    responseWeather.setWeatherIcon(weatherMap.get("icon").toString().toLowerCase());
                    if (feelsLikeMap.get("day") instanceof Integer) {
                        responseWeather.setFeelingBy(((Integer) feelsLikeMap.get("day")).longValue());
                    } else {
                        responseWeather.setFeelingBy(Math.round((Double) feelsLikeMap.get("day")));
                    }
                    if (innerMap.get("wind_speed") instanceof Integer) {
                        additionalParams.setWindSpeed(((Integer) innerMap.get("wind_speed")).longValue());
                    } else {
                        additionalParams.setWindSpeed(Math.round((Double) innerMap.get("wind_speed")));
                    }
                    if (innerMap.get("pressure") instanceof Integer) {
                        additionalParams.setPresure(((Integer) innerMap.get("pressure")).longValue());
                    } else {
                        additionalParams.setPresure(Math.round((Double) innerMap.get("pressure")));
                    }
                    if (innerMap.get("humidity") instanceof Integer) {
                        additionalParams.setHumidity(((Integer) innerMap.get("humidity")).longValue());
                    } else {
                        additionalParams.setHumidity(Math.round((Double) innerMap.get("humidity")));
                    }
                    responseWeather.setAdditionalParams(additionalParams);
                }
            }
            return responseWeather;
        }

    }

    public List<ResponseWeatherWeek> getWeekWeather(RestTemplate restTemplate, RequestWeather requestWeather) throws JsonProcessingException {

        List<ResponseWeatherWeek> arrayList = new ArrayList<>();

        String result = restTemplate
                .exchange(createUri(requestWeather)
                        .queryParam("exclude", excludeWeek)
                        .toUriString(), HttpMethod.GET, createHeaders(), String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> firstMap = objectMapper.readValue(result, Map.class);
        List<Object> array = (List<Object>) firstMap.get("daily");

        for (Object item : array) {
            ResponseWeatherWeek responseWeatherWeek = new ResponseWeatherWeek();
            Map<String, Object> innerMap = (Map<String, Object>) item;
            List<Object> weatherArray = (List<Object>) innerMap.get("weather");
            Map<String, Object> weatherMap = (Map<String, Object>) weatherArray.get(0);
            Map<String, Object> tempMap = (Map<String, Object>) innerMap.get("temp");
            responseWeatherWeek.setDate(innerMap.get("dt").toString());
            responseWeatherWeek.setWeatherStatus(StringUtils.capitalize(weatherMap.get("description").toString()));
            responseWeatherWeek.setWeatherIcon(weatherMap.get("main").toString().toLowerCase());
            if (tempMap.get("day") instanceof Integer) {
                responseWeatherWeek.setTemperature(((Integer) tempMap.get("day")).longValue());
            } else {
                responseWeatherWeek.setTemperature(Math.round((Double) tempMap.get("day")));
            }
            arrayList.add(responseWeatherWeek);
        }
        return arrayList;
    }
}
