package com.example.websocketwebflux.exception;

import com.example.websocketwebflux.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
                if (errors.containsKey(error.getField())) {
                    errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
                } else {
                    errors.put(error.getField(), error.getDefaultMessage());
                }
            }
        );

        return BaseResponse.builder()
            .code(CustomResponseStatus.VALIDATION_FAILED.getErrorCode())
            .errorMessage(CustomResponseStatus.VALIDATION_FAILED.getErrorMessage())
            .result(errors)
            .build();

    }

    @ExceptionHandler(PasswordInvalidException.class)
    public BaseResponse<Object> handlePasswordInvalidException(PasswordInvalidException ex){
        return BaseResponse.builder()
            .code(ex.getErrorCode())
            .errorMessage(ex.getErrorMessage())
            .build();
    }

}
