package com.example.xcodesoftwarerecruitment.domain;

import com.example.xcodesoftwarerecruitment.domain.model.CurrencyRequest;
import com.example.xcodesoftwarerecruitment.domain.model.CurrencyRequestHandled;
import com.example.xcodesoftwarerecruitment.domain.model.CurrentCurrencyValue;
import com.example.xcodesoftwarerecruitment.infrastructure.CurrencyRepository;
import com.example.xcodesoftwarerecruitment.infrastructure.NBPApiConnector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyFacade {

	private final CurrencyRepository currencyRepository;
	private final NBPApiConnector nbpApiConnector;

	public Optional<CurrentCurrencyValue> getCurrentCurrencyValue(final CurrencyRequest currencyRequest) {
		final Optional<Double> value = this.nbpApiConnector.getCurrentCurrencyValue(currencyRequest.getCurrency());
		if (value.isPresent()) {
			this.currencyRepository.saveCurrencyRequest(new CurrencyRequestHandled(
					currencyRequest.getCurrency(),
					currencyRequest.getName(),
					LocalDateTime.now(),
					value.get()));
			return Optional.of(new CurrentCurrencyValue(value.get()));
		}
		return Optional.empty();
	}

	public List<CurrencyRequestHandled> getCurrencyRequestsHistory() {
		return this.currencyRepository.getCurrencyRequests();
	}
}
