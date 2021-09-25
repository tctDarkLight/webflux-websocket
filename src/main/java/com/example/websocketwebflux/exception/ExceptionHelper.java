package com.example.websocketwebflux.exception;

import com.example.websocketwebflux.model.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
/*@Component
@Order(-2)*/
public class ExceptionHelper {

    @ExceptionHandler(value = {InvalidFirebaseTokenException.class})
    public BaseResponse<Object> handleInvalidInputException(InvalidFirebaseTokenException ex) {
        return BaseResponse.builder()
            .code(ex.getErrorCode())
            .errorMessage(ex.getErrorMessage())
            .build();
    }
}
