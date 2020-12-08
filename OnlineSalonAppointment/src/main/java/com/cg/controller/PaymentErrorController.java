package com.cg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.exception.PaymentNotFoundException;
import com.cg.exception.DuplicatePaymentException;

@RestControllerAdvice
public class PaymentErrorController {
	
	@ResponseStatus(value = HttpStatus.IM_USED, reason = "already exits")
	@ExceptionHandler(DuplicatePaymentException.class)
	public void DuplicateException() {

	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "payment not found")
	@ExceptionHandler(PaymentNotFoundException.class)
	public void PaymentException() {

	}

}
