package org.example.javalibrarywithjwt.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

     /*
    Returnerar felmeddelande till användaren!
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(
            MethodArgumentNotValidException e){

        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Validation Failed!");
        pd.setDetail("One or more fields are invalid!");
        var errors = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of(
                        "field", fe.getField(),
                        "rejectedValue", fe.getRejectedValue(),
                        "message", fe.getDefaultMessage()
                ))
                .toList();

        pd.setProperty("errors", errors);
        pd.setProperty("timestamp", OffsetDateTime.now());

        return pd;

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(
            ConstraintViolationException e
    ){
        var pd  = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Constraint Failed!");
        pd.setDetail("Request parameters/path variables are invalid!");
        var errors = e.getConstraintViolations().stream()
                .map(cv -> Map.of(
                        "property", cv.getPropertyPath().toString(),
                        "invalidValue", cv.getInvalidValue().toString(),
                        "message", cv.getMessage()
                ))
                .toList();

        pd.setProperty("errors", errors);
        pd.setProperty("timestamp", OffsetDateTime.now());

        return pd;
    }

}
