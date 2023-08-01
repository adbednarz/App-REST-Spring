package com.example.xcodesoftwarerecruitment.infrastructure;

import com.example.xcodesoftwarerecruitment.domain.CurrencyFacade;
import com.example.xcodesoftwarerecruitment.domain.model.CurrencyNotFoundException;
import com.example.xcodesoftwarerecruitment.domain.model.CurrencyRequest;
import com.example.xcodesoftwarerecruitment.domain.model.CurrencyRequestHandled;
import com.example.xcodesoftwarerecruitment.domain.model.CurrentCurrencyValue;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/currencies")
@AllArgsConstructor
class CurrencyController {

	private final CurrencyFacade currencyFacade;

	@PostMapping( "/get-current-currency-value-command")
	ResponseEntity<CurrentCurrencyValue> getCurrentCurrencyValue(@RequestBody @NonNull final CurrencyRequest currencyRequest) {
		return ResponseEntity.of(this.currencyFacade.getCurrentCurrencyValue(currencyRequest));
	}

	@GetMapping( "/requests")
	List<CurrencyRequestHandled> getCurrencyRequestsHistory() {
		return this.currencyFacade.getCurrencyRequestsHistory();
	}

	@ExceptionHandler(CurrencyNotFoundException.class)
	ResponseEntity<String> handleCurrencyNotFoundException(final Exception e) {
		return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
	}

}
