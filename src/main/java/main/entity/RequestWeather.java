package main.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"lat", "lon"})
@Data
public class RequestWeather {

    @JsonProperty("day")
    private Instant day;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lon")
    private Double lon;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
