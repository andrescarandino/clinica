package ar.com.dh.clinica.Exceptions;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger Logger = LogManager.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ClinicaErrorResponse>emitirBadRequestException(BadRequestException e){
        e.printStackTrace();
        Logger.error("mensaje de error BadRequestException -->" + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ClinicaErrorResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ClinicaErrorResponse>emitirNotFoundException(NotFoundException e){
        e.printStackTrace();
        Logger.error("mensaje de error IllegalArgumentException -->" + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ClinicaErrorResponse(e.getCode(), e.getMessage()));
    }
}
