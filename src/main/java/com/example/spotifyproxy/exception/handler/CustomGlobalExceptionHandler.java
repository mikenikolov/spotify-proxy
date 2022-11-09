package com.example.spotifyproxy.exception.handler;

import com.example.spotifyproxy.exception.ArtistNotFoundException;
import com.example.spotifyproxy.exception.UnauthorizedException;
import com.example.spotifyproxy.exception.entity.ExceptionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        ExceptionEntity entity = new ExceptionEntity();
        entity.setStatus(HttpStatus.UNAUTHORIZED.name())
                .setStatusCode(HttpStatus.UNAUTHORIZED.value())
                .setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ArtistNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<Object> handleArtistNotFoundException(ArtistNotFoundException ex) {
        ExceptionEntity entity = new ExceptionEntity();
        entity.setStatus(HttpStatus.NOT_FOUND.name())
                .setStatusCode(HttpStatus.NOT_FOUND.value())
                .setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(entity, HttpStatus.UNAUTHORIZED);
    }
}
