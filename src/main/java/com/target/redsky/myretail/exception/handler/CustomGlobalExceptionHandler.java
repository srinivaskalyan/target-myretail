package com.target.redsky.myretail.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFoundException(Exception errorMessage, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse("Invalid product id", errorMessage.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FailedToUpdateProductException.class)
	public ResponseEntity<Object> handleFailedToUpdateProductDatabaseException(Exception errorMessage, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse("Failed to update product", errorMessage.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductPriceNotFoundInDBException.class)
	public ResponseEntity<Object> handleProductPriceNotFoundInDBException(Exception errorMessage, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse("Product not available in DB", errorMessage.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
