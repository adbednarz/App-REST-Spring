package com.example.xcodesoftwarerecruitment.domain.model;

public class CurrencyNotFoundException extends RuntimeException {
	public CurrencyNotFoundException(final String message) {
		super(message);
	}
}
