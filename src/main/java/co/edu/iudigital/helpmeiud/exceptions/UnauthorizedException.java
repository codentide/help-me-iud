package co.edu.iudigital.helpmeiud.exceptions;

public class UnauthorizedException extends RestException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(ErrorDTO errorDto) {
        super(errorDto);
    }
}
