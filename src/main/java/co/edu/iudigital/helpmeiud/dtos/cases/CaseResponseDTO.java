package co.edu.iudigital.helpmeiud.dtos.cases;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CaseResponseDTO {

    Long id;
    @JsonProperty("date_time")
    LocalDateTime dateTime;
    Long latitude;
    Long longitude;
    Long altitude;
    @JsonProperty("is_visible")
    Boolean isVisible;
    String description;
    @JsonProperty("map_url")
    String mapUrl;
    @JsonProperty("rmi_url")
    String rmiUrl;
    String username;
    @JsonProperty("crime_name")
    String crimeName;


}
