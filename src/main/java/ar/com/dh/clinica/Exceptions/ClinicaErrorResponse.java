package ar.com.dh.clinica.Exceptions;

public class ClinicaErrorResponse {
    private String code;
    private String message;

    public ClinicaErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ClinicaErrorResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
