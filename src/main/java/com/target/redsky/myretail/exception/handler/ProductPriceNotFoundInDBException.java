package com.target.redsky.myretail.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductPriceNotFoundInDBException extends RuntimeException {

	private static final long serialVersionUID = 1031498820253228511L;

	private String message;

	public ProductPriceNotFoundInDBException() {
	}

	public ProductPriceNotFoundInDBException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
