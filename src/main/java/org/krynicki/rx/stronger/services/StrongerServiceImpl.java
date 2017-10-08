package org.krynicki.rx.stronger.services;

import java.math.BigDecimal;
import java.util.Calendar;

import jdk.dynalink.linker.ConversionComparator;
import org.springframework.beans.factory.annotation.Autowired;

import org.krynicki.rx.rates.adapter.ExchangeRatesAdapter;

import org.krynicki.rx.model.ExchangeRatesResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import org.springframework.stereotype.Component;


@Component
public class StrongerServiceImpl implements StrongerService {

	@Autowired
	private ExchangeRatesAdapter ratesAdapter;

	public Single<Boolean> isStronger(final String baseCurrency, final String counterCurrency) {

		return Observable.zip(
				ratesAdapter.getExchangeRates(baseCurrency).toObservable(),
				ratesAdapter.getExchangeRates(baseCurrency, getYesterday()).toObservable(),
				new BiFunction<ExchangeRatesResponse, ExchangeRatesResponse, Boolean>() {
					public Boolean apply(ExchangeRatesResponse t1, ExchangeRatesResponse t2) throws Exception {

						BigDecimal todayRate = t1.getRate(counterCurrency);
						BigDecimal yesterdayRate = t2.getRate(counterCurrency);

						if (todayRate == null || yesterdayRate == null) {
							//throw new CurrencyNotFoundException();
						}

						return todayRate.compareTo(yesterdayRate) > 0;
					}
		}).toSingle();
	}

	private Calendar getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30);
		return calendar;
	}
}
