package org.repoaggr.svnbrk.configuration;

import org.repoaggr.svnbrk.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.repoaggr.svnbrk.configuration.Constants.STATUS_FAIL;

@RestController
public class BrokerErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    ResponseEntity<ErrorResponse> error() {
        return new ResponseEntity<>(
                new ErrorResponse(STATUS_FAIL, "Resource not found"),
                HttpStatus.NOT_FOUND
        );
    }

    @Override
    public String getErrorPath() { return PATH; }
}
