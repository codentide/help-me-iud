package co.edu.iudigital.helpmeiud.exceptions;

public class RestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ErrorDTO errorDTO;

    public RestException() {
        super();
    }

    public RestException(ErrorDTO errorDTO) {
        super(errorDTO.getError());
        this.errorDTO = errorDTO;
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
