package co.edu.iudigital.helpmeiud.dtos.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class ProfileRequestDTO {

    String name;

    @JsonIgnore
    String image;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("birth_date")
    LocalDate birthDate;

    @JsonProperty("social_web")
    Boolean socialWeb;
}
