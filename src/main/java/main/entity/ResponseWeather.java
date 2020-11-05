package main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"city", "temperature", "weatherStatus", "weatherIcon", "feelingBy", "additionalParams"})
@Data
public class ResponseWeather {

    @JsonProperty("city")
    private String city;
    @JsonProperty("temperature")
    private Long temperature;
    @JsonProperty("weatherStatus")
    private String weatherStatus;
    @JsonProperty("weatherIcon")
    private String weatherIcon;
    @JsonProperty("feelingBy")
    private Long feelingBy;
    @JsonProperty("additionalParams")
    private AdditionalParams additionalParams;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
