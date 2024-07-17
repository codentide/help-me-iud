package co.edu.iudigital.helpmeiud.config;

import co.edu.iudigital.helpmeiud.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> getGenericException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorDTO errorDTO = ErrorDTO
                .getErrorDTO("Error Inesperado", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({InternalServerException.class})
    public ResponseEntity<ErrorDTO> getGenericException(InternalServerException e) {
        log.error(e.getMessage(), e);
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDTO.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorDTO> getNotFoundException(NotFoundException e) {
        log.info(e.getMessage());
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorDTO.setDate(LocalDateTime.now());
        errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorDTO> getBadRequestException(BadRequestException e) {
        log.info(e.getErrorDTO().getMessage());
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorDTO.setDate(LocalDateTime.now());
        errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ErrorDTO> getUnauthorizedException(UnauthorizedException e) {
        log.info(e.getErrorDTO().getMessage());
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        errorDTO.setDate(LocalDateTime.now());
        errorDTO.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ErrorDTO> getForbiddenException(ForbiddenException e) {
        log.info(e.getErrorDTO().getMessage());
        ErrorDTO errorDTO = e.getErrorDTO();
        errorDTO.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
        errorDTO.setDate(LocalDateTime.now());
        errorDTO.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorDTO> getAccessDeniedException(org.springframework.security.access.AccessDeniedException e) {
        log.error(e.getMessage(), e);
        ErrorDTO errorDTO = ErrorDTO
                .getErrorDTO("Error de Acceso", e.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }

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
