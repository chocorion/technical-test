package fr.rob.technicaltest.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception) {
        String violations = exception.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + " -> " + violation.getMessage())
                .collect(Collectors.joining(", "));

        return generateResponse(exception, HttpStatus.BAD_REQUEST, violations);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,HttpHeaders headers, HttpStatus status, WebRequest request) {
        return generateResponse(exception, HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " -> " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return generateResponse(exception, HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Object> handleNotFoundException(NotFound exception) {
        return generateResponse(exception, HttpStatus.NOT_FOUND, exception.getStatusText());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception exception, WebRequest request) {
        return generateResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected exception : " + exception.getMessage());
    }

    private ResponseEntity<Object> generateResponse(Exception ex, HttpStatus status, String message) {
        Map<String, Object> body = Map.of(
                "status", status,
                "error", ex.getClass().getSimpleName(),
                "message", message
        );

        return new ResponseEntity<>(body, status);
    }
}
