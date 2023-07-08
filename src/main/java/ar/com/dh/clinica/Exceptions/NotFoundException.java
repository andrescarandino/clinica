package ar.com.dh.clinica.Exceptions;

public class NotFoundException extends Exception {

    private String code;

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
