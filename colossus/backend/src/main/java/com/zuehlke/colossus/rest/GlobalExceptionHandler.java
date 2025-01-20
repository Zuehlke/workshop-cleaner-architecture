package com.zuehlke.colossus.rest;

import com.zuehlke.colossus.exceptions.ApplicationException;
import com.zuehlke.colossus.exceptions.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.zuehlke.colossus.exceptions.ApplicationException.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ApplicationException.class })
    @ResponseBody
    public ResponseEntity<RestError> handleApplicationException(ApplicationException applicationException) {

        String message = null;
        HttpStatus httpStatus = null;
        switch(applicationException.code()) {
            case UNAUTHORIZED : httpStatus = HttpStatus.UNAUTHORIZED;
                                message = "Authentication failed";
                                break;
            default:    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = "Internal server error";
                        break;
        }

        RestError restError = new RestError(httpStatus.toString(), message);
        return ResponseEntity.status(httpStatus).body(restError);
    }

    @ExceptionHandler({ SystemException.class })
    @ResponseBody
    public ResponseEntity<RestError> handleSystemException(SystemException systemException) {

        String message = null;
        HttpStatus httpStatus = null;
        switch(systemException.code()) {
            case UNAUTHORIZED : httpStatus = HttpStatus.UNAUTHORIZED;
                message = "Authentication failed";
                break;
            default:    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "Internal server error";
                break;
        }

        RestError restError = new RestError(httpStatus.toString(), message);
        return ResponseEntity.status(httpStatus).body(restError);
    }
}
