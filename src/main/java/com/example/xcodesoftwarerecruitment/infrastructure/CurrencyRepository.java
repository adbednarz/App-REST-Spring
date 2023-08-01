package com.example.xcodesoftwarerecruitment.infrastructure;

import com.example.xcodesoftwarerecruitment.domain.model.CurrencyRequestHandled;
import lombok.NonNull;

import java.util.List;

public interface CurrencyRepository {

	void saveCurrencyRequest(@NonNull final CurrencyRequestHandled currencyRequestHandled);

	List<CurrencyRequestHandled> getCurrencyRequests();

}
