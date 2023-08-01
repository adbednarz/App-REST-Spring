package com.example.xcodesoftwarerecruitment.infrastructure;

import com.example.xcodesoftwarerecruitment.domain.model.CurrencyRequestHandled;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
class CurrencyJdbcRepository implements CurrencyRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void saveCurrencyRequest(@NonNull final CurrencyRequestHandled currencyRequestHandled) {
		this.jdbcTemplate.update("INSERT INTO CurrencyRequests (currency, name, date, \"value\") VALUES (?, ?, ?, ?)",
				currencyRequestHandled.getCurrency(),
				currencyRequestHandled.getName(),
				currencyRequestHandled.getDate(),
				currencyRequestHandled.getValue());
	}

	@Override
	public List<CurrencyRequestHandled> getCurrencyRequests() {
		return this.jdbcTemplate.query("SELECT * FROM CurrencyRequests", (rs, rowNum) -> new CurrencyRequestHandled(
				rs.getString("currency"),
				rs.getString("name"),
				rs.getTimestamp("date").toLocalDateTime(),
				rs.getDouble("value")));
	}

}
