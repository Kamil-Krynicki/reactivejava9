package org.krynicki.rx.rates.services;

import org.krynicki.rx.forex.model.ExchangeRatesResponse;
import io.reactivex.Single;

public interface ExchangeRatesService {
	Single<ExchangeRatesResponse> getExchangeRates(String base);
}
