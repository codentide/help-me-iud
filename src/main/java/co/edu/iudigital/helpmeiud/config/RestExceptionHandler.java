package co.edu.iudigital.helpmeiud.config;

import co.edu.iudigital.helpmeiud.exceptions.BadRequestException;
import co.edu.iudigital.helpmeiud.exceptions.ErrorDTO;
import co.edu.iudigital.helpmeiud.exceptions.InternalServerException;
import co.edu.iudigital.helpmeiud.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> getGenericException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorDTO errorDTO = ErrorDTO.getErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Ocurrió un error inesperado.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({InternalServerException.class})
    public ResponseEntity<ErrorDTO> getGenericException(InternalServerException e) {
        log.error(e.getMessage(), e);
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setMessage("Ocurrió un error interno en el servidor.");
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorDTO> getNotFoundException(NotFoundException e) {
        log.info(e.getMessage());
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setMessage("El recurso solicitado no fue encontrado.");
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorDTO> getBadRequestException(BadRequestException e) {
        log.info(e.getErrorDTO().getMessage());
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setMessage("La solicitud contiene datos inválidos.");
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
/*
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ErrorDTO> getUnauthorizedException(UnauthorizedException e) {
        log.info(e.getErrorDTO().getMessage());
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setMessage("No tiene autorización para realizar esta acción.");
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }
*/
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            org.springframework.web.bind.MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            errorMessage.append(fieldErrors.get(0).getDefaultMessage());
        } else {
            errorMessage.append("Ocurrió un error al procesar la solicitud. Por favor, verifique e intente de nuevo.");
        }
        ErrorDTO errorInfo = ErrorDTO.getErrorDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage.toString(),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
