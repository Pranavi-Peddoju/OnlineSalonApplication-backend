package com.cg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.exception.AppointmentIdNotFoundException;
import com.cg.exception.InvalidAppointmentException;

@RestControllerAdvice
public class AppointmentErrorController {


	@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="Appointment with this id not present")
    @ExceptionHandler(value=AppointmentIdNotFoundException.class)
    public void handleException2(AppointmentIdNotFoundException e) {
        
    }
	@ResponseStatus(value=HttpStatus.NO_CONTENT,reason="Invalid Appointment")
    @ExceptionHandler(value=InvalidAppointmentException.class)
    public void handleException3(InvalidAppointmentException e) {
        
    }
}
