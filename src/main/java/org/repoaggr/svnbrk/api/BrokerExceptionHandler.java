package org.repoaggr.svnbrk.api;

import org.repoaggr.svnbrk.model.ErrorResponse;
import org.repoaggr.svnbrk.model.exceptions.CacheException;
import org.repoaggr.svnbrk.model.exceptions.RemoteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.repoaggr.svnbrk.configuration.Constants.STATUS_FAIL;

@ControllerAdvice
public class BrokerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CacheException.class)
    protected ResponseEntity<ErrorResponse> handleCacheException(CacheException e) {
        return new ResponseEntity<>(
                new ErrorResponse(STATUS_FAIL, e.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(RemoteException.class)
    protected ResponseEntity<ErrorResponse> handleRemoteException(RemoteException e) {
        return new ResponseEntity<>(
                new ErrorResponse(STATUS_FAIL, e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
