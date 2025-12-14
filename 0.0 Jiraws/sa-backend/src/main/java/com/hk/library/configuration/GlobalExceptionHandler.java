package com.hk.library.configuration;

import com.hk.library.Book.model.exception.BookCreationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> catchAny(Exception ex) {

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage() // En production, mets un message générique
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(pd);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> catchBadRequest(BadRequestException ex) {

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage() // En production, mets un message générique
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(pd);
    }

    @ExceptionHandler(BookCreationException.class)
    public ResponseEntity<ProblemDetail> catchBookCreation(BookCreationException ex) {

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage() // En production, mets un message générique
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(pd);
    }
}
