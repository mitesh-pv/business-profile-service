package com.miteshpv.bizprofileservice.bizprofile.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class BusinessProfileExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiErrorResponse> profileNotFoundException(
            final ResourceNotFoundException ex, final WebRequest webRequest) {
        log.error("TaxID Not Found, status : {}, clientId : {}", ex.getStatus(), ex.getTaxId());
        ApiErrorResponse errorResponse =
                new ApiErrorResponse("404", "Subscription not found with tax identifier : " + ex.getTaxId());
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }
}
