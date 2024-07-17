package co.edu.iudigital.helpmeiud.dtos.cases;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CaseRequestDTO {
    @JsonProperty("date_time")
    LocalDateTime dateTime;
    Long latitude;
    Long longitude;
    Long altitude;
    String description;
    @JsonProperty("map_url")
    String mapUrl;
    @JsonProperty("rmi_url")
    String rmiUrl;
    @NotNull(message = "ID del usuario requerido")
    @JsonIgnore
    Long userId;

    @NotNull(message = "Delito requerido")
    @JsonProperty("crime_name")
    String crimeName;

    @JsonIgnore
    Long crimeId;
}
