package com.example.xcodesoftwarerecruitment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CurrencyTable {
	@Getter
	private List<Currency> rates;
}
