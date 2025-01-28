package nep.nitin.restapi.exceptions;

import lombok.extern.slf4j.Slf4j;
import nep.nitin.restapi.io.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 *Global exception handler for all the exception
 * @author Nitin Paudel
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorObject handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error("Throwing the ResourceNotFoundException from the GlobalExceptionHandler", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("Data_Not_Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }
}
