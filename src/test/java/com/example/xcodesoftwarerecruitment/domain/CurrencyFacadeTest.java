package com.example.xcodesoftwarerecruitment.domain;

import com.example.xcodesoftwarerecruitment.domain.model.CurrencyRequest;
import com.example.xcodesoftwarerecruitment.domain.model.CurrentCurrencyValue;
import com.example.xcodesoftwarerecruitment.infrastructure.CurrencyRepository;
import com.example.xcodesoftwarerecruitment.infrastructure.NBPApiConnector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyFacadeTest {

	@InjectMocks
	private CurrencyFacade sut;

	@Mock
	private NBPApiConnector nbpApiConnector;

	@Mock
	private CurrencyRepository currencyRepository;

	@Test
	void should_return_currency_value_and_save_request() {
		// given
		final CurrencyRequest currencyRequest = new CurrencyRequest("EUR", "Jan Kowalski");
		when(this.nbpApiConnector.getCurrentCurrencyValue(any())).thenReturn(Optional.of(4.41));

		// when
		final Optional<CurrentCurrencyValue> result = this.sut.getCurrentCurrencyValue(currencyRequest);

		// then
		assertTrue(result.isPresent());
		verify(this.currencyRepository).saveCurrencyRequest(any());
	}

	@Test
	void should_return_empty_optional_and_do_not_save_request() {
		// given
		final CurrencyRequest currencyRequest = new CurrencyRequest("ANY", "Jan Kowalski");
		when(this.nbpApiConnector.getCurrentCurrencyValue(any())).thenReturn(Optional.empty());

		// when
		final Optional<CurrentCurrencyValue> result = this.sut.getCurrentCurrencyValue(currencyRequest);

		// then
		assertFalse(result.isPresent());
		verifyNoMoreInteractions(this.currencyRepository);
	}
}
