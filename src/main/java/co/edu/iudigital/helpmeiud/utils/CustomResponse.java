package co.edu.iudigital.helpmeiud.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter
public class CustomResponse {
    static final long serialVersionUID = 1L;

     int statusCode;
     String message;

    public CustomResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}