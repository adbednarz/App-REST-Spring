package com.example.xcodesoftwarerecruitment.infrastructure;

import com.example.xcodesoftwarerecruitment.domain.model.Currency;
import com.example.xcodesoftwarerecruitment.domain.model.CurrencyNotFoundException;
import com.example.xcodesoftwarerecruitment.domain.model.CurrencyTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NBPApiConnectorTest {

	@InjectMocks
	private NBPApiConnector sut;

	@Mock
	private RestTemplate restTemplate;

	@Test
	void should_return_currency_value() {
		// given
		List<CurrencyTable> body = List.of(new CurrencyTable(List.of(new Currency("EUR", 4.41))));
		ResponseEntity<List<CurrencyTable>> response = new ResponseEntity<>(body, HttpStatusCode.valueOf(200));
		final String URL = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";
		when(restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<CurrencyTable>>() {}))
				.thenReturn(response);

		// when
		final Optional<Double> value = this.sut.getCurrentCurrencyValue("EUR");

		// then
		assertTrue(value.isPresent());
	}

	@Test
	void should_thrown_error_when_currency_code_not_exists() {
		// given
		List<CurrencyTable> body = List.of(new CurrencyTable(List.of(new Currency("EUR", 4.41))));
		ResponseEntity<List<CurrencyTable>> response = new ResponseEntity<>(body, HttpStatusCode.valueOf(200));
		final String URL = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";
		when(restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<CurrencyTable>>() {}))
				.thenReturn(response);

		// when
		assertThrows(CurrencyNotFoundException.class, () -> {
			final Optional<Double> value = this.sut.getCurrentCurrencyValue("ANY");
		});
	}

	@Test
	void should_return_empty_optional_when_response_status_not_successful() {
		// given
		List<CurrencyTable> body = List.of(new CurrencyTable(List.of(new Currency("EUR", 4.41))));
		ResponseEntity<List<CurrencyTable>> response = new ResponseEntity<>(body, HttpStatusCode.valueOf(500));
		final String URL = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";
		when(restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<CurrencyTable>>() {}))
				.thenReturn(response);

		// when
		final Optional<Double> value = this.sut.getCurrentCurrencyValue("EUR");

		// then
		assertEquals(value, Optional.empty());
	}

	@Test
	void should_return_empty_optional_when_response_empty() {
		// given
		List<CurrencyTable> body = List.of();
		ResponseEntity<List<CurrencyTable>> response = new ResponseEntity<>(body, HttpStatusCode.valueOf(200));
		final String URL = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";
		when(restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<CurrencyTable>>() {}))
				.thenReturn(response);

		// when
		final Optional<Double> value = this.sut.getCurrentCurrencyValue("EUR");

		// then
		assertEquals(value, Optional.empty());
	}
}
