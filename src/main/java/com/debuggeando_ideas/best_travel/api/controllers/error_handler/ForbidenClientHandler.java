package com.debuggeando_ideas.best_travel.api.controllers.error_handler;


import com.debuggeando_ideas.best_travel.api.models.responses.BaseErrorResponse;
import com.debuggeando_ideas.best_travel.api.models.responses.ErrorResponse;
import com.debuggeando_ideas.best_travel.util.exceptions.ForbiddenCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbidenClientHandler {

    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse handleIdNotFound(ForbiddenCustomerException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

}
