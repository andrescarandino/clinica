package ar.com.dh.clinica.Exceptions;

public class BadRequestException extends Exception {

    private String code;

    public BadRequestException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
