package co.edu.iudigital.helpmeiud.dtos.users;

import co.edu.iudigital.helpmeiud.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class UserResponseDTO {

    Long id;
    String username;
    String name;
    String lastName;
    String image;
    Boolean enabled;
    List<String> roles;

    @JsonProperty("birth_date")
    LocalDate birthDate;
    @JsonProperty("social_web")
    Boolean socialWeb;

    @JsonIgnore
    String password;
}
