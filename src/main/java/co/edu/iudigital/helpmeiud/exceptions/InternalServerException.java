package co.edu.iudigital.helpmeiud.exceptions;


public class InternalServerException extends RestException {
    private static final long serialVersionUID = 1L;
    private String error;

    public InternalServerException(String message, String error, Exception e) {
        super(message, e);
        this.error = error;
    }

    public InternalServerException(String message, Exception e) {
        super(message, e);
    }

    public InternalServerException() {
        super();
    }

    public InternalServerException(ErrorDTO errorDTO) {
        super(errorDTO);
    }

    public String getError() {
        return error;
    }
}
