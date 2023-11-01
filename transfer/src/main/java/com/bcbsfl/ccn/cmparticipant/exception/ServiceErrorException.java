package com.bcbsfl.ccn.cmparticipant.exception;

import org.springframework.http.HttpStatus;

public class ServiceErrorException extends RuntimeException{

	private final ApiErrorResponse apiErrorResponse;
	private HttpStatus httpStatus;

	public ServiceErrorException(ApiErrorResponse apiErrorResponse, HttpStatus httpStatus) {
		
		this.apiErrorResponse = apiErrorResponse;
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ApiErrorResponse getApiErrorResponse() {
		return apiErrorResponse;
	}

}
