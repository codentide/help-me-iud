package co.edu.iudigital.helpmeiud.dtos.crimes;

import co.edu.iudigital.helpmeiud.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CrimeResponseDTO {

    Long id;
    String name;
    String description;
    @JsonIgnore
    String user;

    @JsonProperty("created_at")
    LocalDateTime createdAt;
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
}
