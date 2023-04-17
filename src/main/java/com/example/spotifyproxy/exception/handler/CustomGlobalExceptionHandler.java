package com.example.spotifyproxy.exception.handler;

import com.example.spotifyproxy.exception.ArtistNotFoundException;
import com.example.spotifyproxy.exception.IncorrectPageException;
import com.example.spotifyproxy.exception.UnauthorizedException;
import com.example.spotifyproxy.exception.entity.ExceptionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UnauthorizedException.class)
    protected ResponseEntity<ExceptionEntity> handleUnauthorizedException(Exception ex) {
        ExceptionEntity entity = new ExceptionEntity()
                .setStatus(HttpStatus.UNAUTHORIZED.name())
                .setStatusCode(HttpStatus.UNAUTHORIZED.value())
                .setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ArtistNotFoundException.class)
    protected ResponseEntity<ExceptionEntity> handleDefaultNotFoundException(Exception ex) {
        ExceptionEntity entity = new ExceptionEntity()
                .setStatus(HttpStatus.NOT_FOUND.name())
                .setStatusCode(HttpStatus.NOT_FOUND.value())
                .setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IncorrectPageException.class)
    protected ResponseEntity<ExceptionEntity> handleDefaultBadRequest(Exception ex) {
        ExceptionEntity entity = new ExceptionEntity()
                .setStatus(HttpStatus.BAD_REQUEST.name())
                .setStatusCode(HttpStatus.BAD_REQUEST.value())
                .setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.BAD_REQUEST);
    }
}
