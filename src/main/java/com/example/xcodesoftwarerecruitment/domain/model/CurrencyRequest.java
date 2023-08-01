package com.example.xcodesoftwarerecruitment.domain.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class CurrencyRequest {
	@NonNull
	String currency;
	@NonNull
	String name;
}
