package co.edu.iudigital.helpmeiud.exceptions;

public class ForbiddenException extends RestException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(ErrorDTO errorDTO) {
        super(errorDTO);
    }

    public ForbiddenException(String msg) {
        super(msg);
    }
}
