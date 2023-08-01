package com.example.xcodesoftwarerecruitment.infrastructure;

import com.example.xcodesoftwarerecruitment.domain.model.CurrencyRequestHandled;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyJdbcRepositoryTest {

	private CurrencyRepository currencyRepository;
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setup() throws ClassNotFoundException {
		final JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:dev;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE");
		Class.forName("org.h2.Driver");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.jdbcTemplate.execute("DROP ALL OBJECTS");
		this.jdbcTemplate.execute("""
			CREATE TABLE CurrencyRequests (
				id INT AUTO_INCREMENT PRIMARY KEY,
				currency VARCHAR(3) NOT NULL,
				name VARCHAR(255) NOT NULL,
				date TIMESTAMP NOT NULL,
				"value" DOUBLE NOT NULL
			);
		""");
		this.currencyRepository = new CurrencyJdbcRepository(this.jdbcTemplate);
	}

	@Test
	void should_save_currency_request() {
		// given
		final CurrencyRequestHandled currencyRequestHandled = new CurrencyRequestHandled("EUR", "Jan Kowalski", LocalDateTime.now(), 4.42);

		// when
		this.currencyRepository.saveCurrencyRequest(currencyRequestHandled);

		// then
		assertEquals(this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM CurrencyRequests", Integer.class), 1);
	}

	@Test
	void should_return_saved_currency_requests() {
		// given
		this.jdbcTemplate.update("INSERT INTO CurrencyRequests (currency, name, date, \"value\") VALUES (?, ?, ?, ?)",
				"EUR", "Jan Kowalski", LocalDateTime.now(), 4.41);
		this.jdbcTemplate.update("INSERT INTO CurrencyRequests (currency, name, date, \"value\") VALUES (?, ?, ?, ?)",
				"HUF", "Piotr Nowak", LocalDateTime.now(), 0.01);

		// when
		final List<CurrencyRequestHandled> result = this.currencyRepository.getCurrencyRequests();

		// then
		assertEquals(result.size(), 2);

	}
}
