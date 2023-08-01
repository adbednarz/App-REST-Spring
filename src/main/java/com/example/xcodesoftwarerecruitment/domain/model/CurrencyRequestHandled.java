package com.example.xcodesoftwarerecruitment.domain.model;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CurrencyRequestHandled {
	String currency;
	String name;
	LocalDateTime date;
	double value;
}
