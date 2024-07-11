package co.edu.iudigital.helpmeiud.exceptions;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter
@Builder
public class ErrorDTO {

        static final long serialVersionUID = 1L;
        String error;
        String message;
        int status;
        LocalDateTime date;

        /**
         * Método estático para construir un ErrorDTO.
         *
         * @param error   Descripción breve del error.
         * @param message Mensaje detallado del error.
         * @param status  Código de estado HTTP.
         * @return Objeto ErrorDTO.
         */
        public static ErrorDTO getErrorDTO(String error, String message, int status) {
                return ErrorDTO.builder()
                        .error(error)
                        .message(message)
                        .status(status)
                        .date(LocalDateTime.now())
                        .build();
        }
}
