package co.edu.iudigital.helpmeiud.dtos.crimes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CrimeRequestDTO {
    @NotNull(message = "Nombre requerido")
    String name;
    String description;
}
