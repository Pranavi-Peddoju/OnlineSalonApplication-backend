package com.cg.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.exception.DuplicateRecordException;
import com.cg.exception.InvalidAccessException;
import com.cg.exception.InvalidInputException;
import com.cg.exception.InvalidPasswordException;
import com.cg.exception.InvalidUserException;
import com.cg.exception.NoSuchServiceException;
import com.cg.exception.NoSuchUserException;
import com.cg.model.ErrorResponse;

/**
* The ErrorController class Handles All the Exceptions Raised
*
* @author   :Peddoju Teja Pranavi
* @version  :1.0
* @since    :2020-12-01 
**/
@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { NoSuchServiceException.class })
	public ErrorResponse handleNoServiceException(NoSuchServiceException exception, HttpServletRequest request) {
		return new ErrorResponse(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				HttpStatus.NOT_FOUND.value(), request.getRequestURI());
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { NoSuchUserException.class })
	public ErrorResponse handleNoSuchUserException(NoSuchUserException exception, HttpServletRequest request) {
		return new ErrorResponse(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				HttpStatus.NOT_FOUND.value(), request.getRequestURI());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = InvalidAccessException.class)
	public ErrorResponse handleInvalidAccessException(InvalidAccessException exception, HttpServletRequest request) {
		return new ErrorResponse(new Date(), exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = InvalidInputException.class)
	public ErrorResponse handleInvalidInputException(InvalidInputException exception, HttpServletRequest request) {
		return new ErrorResponse(new Date(), exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = InvalidPasswordException.class)
	public ErrorResponse handleInvalidPasswordException(InvalidPasswordException exception,
			HttpServletRequest request) {
		return new ErrorResponse(new Date(), exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = InvalidUserException.class)
	public ErrorResponse handleInvalidUserException(InvalidUserException exception, HttpServletRequest request) {
		return new ErrorResponse(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				HttpStatus.NOT_FOUND.value(), request.getRequestURI());
	}

	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(value = DuplicateRecordException.class)
	public ErrorResponse handleDuplicateRecordException(DuplicateRecordException exception,
			HttpServletRequest request) {
		return new ErrorResponse(new Date(), exception.getMessage(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
				HttpStatus.NOT_ACCEPTABLE.value(), request.getRequestURI());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse errors = new ErrorResponse(new Date(), "Sorry This Method is not Supported", ex.getMessage(), status.value(),
				request.getDescription(false));
		return new ResponseEntity<>(errors, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse errors = new ErrorResponse(new Date(), "Please provide JSON Format", ex.getMessage(),
				status.value(), request.getDescription(false));
		return new ResponseEntity<>(errors, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		ErrorResponse errors = new ErrorResponse(new Date(), "Please provide valid details", result.getFieldError().getDefaultMessage().toString(),
				status.value(), request.getDescription(false));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
