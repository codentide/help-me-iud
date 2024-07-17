package co.edu.iudigital.helpmeiud.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MyCustomException", description = "Excepción personalizada para \"NotFound\"")
public class NotFoundException extends RestException{
    private static final long serialVersionUID = 1L;
    public NotFoundException() {
        super();
    }
    public NotFoundException(ErrorDTO errorDTO) {
        super(errorDTO);
    }
}
