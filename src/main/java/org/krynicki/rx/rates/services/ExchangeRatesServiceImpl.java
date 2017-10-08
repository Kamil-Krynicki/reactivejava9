package org.krynicki.rx.rates.services;

import org.krynicki.rx.rates.adapter.ExchangeRatesAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import org.krynicki.rx.model.ExchangeRatesResponse;

import io.reactivex.Single;
import org.springframework.stereotype.Component;


@Component
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

	@Autowired
	private ExchangeRatesAdapter exchangeRatesAdapter;

	public Single<ExchangeRatesResponse> getExchangeRates(final String base) {
		
		return exchangeRatesAdapter.getExchangeRates(base);
	}	
}
