package com.cristopherandre.goldenraspberryawardsapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cristopherandre.goldenraspberryawardsapi.exceptions.RecordNotFoundException;

@ControllerAdvice
public class RecordNotFoundAdvise {

    @ResponseBody
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(RecordNotFoundException ex) {
        return ex.getMessage();
    }

}
