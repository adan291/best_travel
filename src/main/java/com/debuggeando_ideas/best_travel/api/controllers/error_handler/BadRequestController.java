package com.debuggeando_ideas.best_travel.api.controllers.error_handler;

import com.debuggeando_ideas.best_travel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler
    public String handleIdNotFound(IdNotFoundException exception){
        return exception.getMessage();
    }
}
