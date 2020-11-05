package main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"date", "weatherStatus", "weatherIcon", "temperature"})
@Data
public class ResponseWeatherWeek {

    @JsonProperty("date")
    private String date;
    @JsonProperty("weatherStatus")
    private String weatherStatus;
    @JsonProperty("weatherIcon")
    private String weatherIcon;
    @JsonProperty("temperature")
    private Long temperature;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}