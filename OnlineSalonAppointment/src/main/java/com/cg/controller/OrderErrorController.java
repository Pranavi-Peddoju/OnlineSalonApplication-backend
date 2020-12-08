package com.cg.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.exception.DuplicateOrderException;
import com.cg.exception.OrderNotFoundException;
import com.cg.model.ErrorResponse;

@RestControllerAdvice
public class OrderErrorController extends ResponseEntityExceptionHandler {

	// handles duplicate global exception
	@ResponseStatus(value = HttpStatus.IM_USED)
	@ExceptionHandler(value = DuplicateOrderException.class)
	public void DuplicateExceptionHandling(DuplicateOrderException e) {

	}

	// handles global exception if order is not found
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = OrderNotFoundException.class)
	public ErrorResponse orderNotFoundHandleException(OrderNotFoundException exception, HttpServletRequest request) {
		return new ErrorResponse(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				HttpStatus.NOT_FOUND.value(), request.getRequestURI());
	}

	// handles if not valid parameters/inputs provided and returns a response message
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		ErrorResponse errors = new ErrorResponse(new Date(), "Please provide valid details",
				result.getFieldError().getDefaultMessage().toString(), status.value(), request.getDescription(false));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	}

}
