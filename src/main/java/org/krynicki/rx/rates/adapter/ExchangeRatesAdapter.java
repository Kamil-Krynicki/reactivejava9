package org.krynicki.rx.rates.adapter;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.krynicki.rx.model.ExchangeRatesResponse;

import com.google.gson.Gson;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ExchangeRatesAdapter {

	private static final String EXCHANGE_RATE_BASE_END_POINT = "http://api.fixer.io/latest?base=%s";

	public Single<ExchangeRatesResponse> getExchangeRates(final String base) {
		
		return Single.create(new SingleOnSubscribe<ExchangeRatesResponse>() {

			public void subscribe(SingleEmitter<ExchangeRatesResponse> subscriber) {
				
				try {

					String endPoint = String.format(EXCHANGE_RATE_BASE_END_POINT, base);
					RestTemplate template = new RestTemplate();
					ExchangeRatesResponse re = template.getForObject(new URI(endPoint), ExchangeRatesResponse.class);

		    		subscriber.onSuccess(re);
		    		
				} catch (Exception e) {
					
					//subscriber.onError(new InternalErrorException());
				}
			}
		});		
	}	
}
