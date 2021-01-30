package hranj.marijan.springbootapp.handlers;

import hranj.marijan.springbootapp.bean.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException e, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .exception(e.getClass().getSimpleName())
                .error(e.getMessage())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
