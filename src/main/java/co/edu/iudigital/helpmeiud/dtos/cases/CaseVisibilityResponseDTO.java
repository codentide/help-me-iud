package co.edu.iudigital.helpmeiud.dtos.cases;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class CaseVisibilityResponseDTO implements Serializable {

    static final long serialVersionUID = 1L;

    @JsonProperty("case_id")
    Long id;

    @NotNull(message = "Visibility required")
    @JsonProperty("is_visible")
    Boolean isVisible;
}
