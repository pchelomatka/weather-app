package main.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"windSpeed", "presure", "humidity"})
@Data
public class AdditionalParams {

    @JsonProperty("windSpeed")
    private Long windSpeed;
    @JsonProperty("presure")
    private Long presure;
    @JsonProperty("humidity")
    private Long humidity;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
