package org.krynicki.rx.rates.services;

import org.krynicki.rx.forex.service.ExchangeRatesAdapterService;
import org.springframework.beans.factory.annotation.Autowired;

import org.krynicki.rx.forex.model.ExchangeRatesResponse;

import io.reactivex.Single;
import org.springframework.stereotype.Component;


@Component
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

	private ExchangeRatesAdapterService exchangeRatesAdapterService;

	@Autowired
	public ExchangeRatesServiceImpl(ExchangeRatesAdapterService exchangeRatesAdapterService) {
		this.exchangeRatesAdapterService = exchangeRatesAdapterService;
	}

	public Single<ExchangeRatesResponse> getExchangeRates(final String base) {
		return exchangeRatesAdapterService.getExchangeRates(base);
	}	
}
