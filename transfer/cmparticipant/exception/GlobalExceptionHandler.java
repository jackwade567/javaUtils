package com.bcbsfl.ccn.cmparticipant.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ServiceErrorException.class)
	public ResponseEntity<Object> errorMessage(ServiceErrorException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getApiErrorResponse().getCode(),
				ex.getApiErrorResponse().getMessage());
		return new ResponseEntity<>(apiErrorResponse, ex.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> errorMessage(MethodArgumentTypeMismatchException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse("600", "Enter valid Input");
		return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<Object> errorMessage(MissingPathVariableException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse("601", "Input doesn't empty and Enter valid Input");
		return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> errorMessage(IllegalArgumentException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse("601", ex.getMessage());
		return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse("601", "invalid input data");
		return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public ResponseEntity<Object> handleTypeMismatchException(HttpMessageNotReadableException ex) {
//		String errorMessage = "Invalid input data type";
//		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
