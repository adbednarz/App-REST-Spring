package com.example.xcodesoftwarerecruitment.infrastructure;

import com.example.xcodesoftwarerecruitment.domain.model.Currency;
import com.example.xcodesoftwarerecruitment.domain.model.CurrencyNotFoundException;
import com.example.xcodesoftwarerecruitment.domain.model.CurrencyTable;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NBPApiConnector {

	private static final String URL = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";
	private final RestTemplate restTemplate;

	public Optional<Double> getCurrentCurrencyValue(final String currencyCode) {
		final ResponseEntity<List<CurrencyTable>> response = this.restTemplate.exchange(
				URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		if (response.getStatusCode().is2xxSuccessful()) {
			final List<CurrencyTable> tables = response.getBody();
			if (tables != null && tables.size() != 0) {
				return Optional.of(this.getCurrentCurrencyValueFromTable(tables.get(0), currencyCode));
			}
		}
		return Optional.empty();
	}

	private Double getCurrentCurrencyValueFromTable(final CurrencyTable currencyTable, final String currencyCode) {
		final Optional<Currency> currency = currencyTable.getRates().stream().filter(rate ->
				rate.getCode().equals(currencyCode)).findFirst();
		if (currency.isEmpty()) {
			throw new CurrencyNotFoundException("Currency code (" + currencyCode + ") not found.");
		} else {
			return currency.get().getMid();
		}
	}
}
