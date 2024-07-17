package co.edu.iudigital.helpmeiud.dtos.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class UserRequestDTO /*implements Serializable*/ {

    // static final long serialVersionUID = 1L;

    @NotNull(message = "Username nombre de usuario")
    @Email(message = "El nombre de usuario debe ser un email")
    String username;

    String name;
    String lastName;
    String password;
    String image;

    @JsonProperty("birth_date")
    LocalDate birthDate;
    @JsonProperty("social_web")
    Boolean socialWeb;

    @JsonIgnore
    Boolean enabled;
    @JsonIgnore
    List<Long> rolesID;
}
