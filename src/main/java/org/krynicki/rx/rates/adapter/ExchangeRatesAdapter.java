package org.krynicki.rx.rates.adapter;

import io.reactivex.*;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.krynicki.rx.model.ExchangeRatesResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ExchangeRatesAdapter {

	private static final String EXCHANGE_RATE_BASE_END_POINT = "http://api.fixer.io/latest?base=%s";
    private static final String HISTORY_RATE_BASE_END_POINT = "http://api.fixer.io/%s?base=%s";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Single<ExchangeRatesResponse> getExchangeRates(final String base) {
		
		return Single.create(new SingleOnSubscribe<ExchangeRatesResponse>() {

            @Override
            public void subscribe(SingleEmitter<ExchangeRatesResponse> emitter) throws Exception {
                try {
                    String endPoint = String.format(EXCHANGE_RATE_BASE_END_POINT, base);
                    RestTemplate template = new RestTemplate();
                    ExchangeRatesResponse re = template.getForObject(new URI(endPoint), ExchangeRatesResponse.class);

                    emitter.onSuccess(re);
                } catch (Exception e) {
                    //subscriber.onError(new InternalErrorException());
                }
            }
		});
	}

    public Single<ExchangeRatesResponse> getExchangeRates(final String base, final Calendar date) {
        return Single.create(new SingleOnSubscribe<ExchangeRatesResponse>() {

            @Override
            public void subscribe(SingleEmitter<ExchangeRatesResponse> emitter) throws Exception {
                try {
                    String endPoint = String.format(HISTORY_RATE_BASE_END_POINT, formatDate(date), base);
                    URI obj = new URI(endPoint);

                    RestTemplate template = new RestTemplate();
                    ExchangeRatesResponse re = template.getForObject(new URI(endPoint), ExchangeRatesResponse.class);

                    emitter.onSuccess(re);
                } catch (Exception e) {
                    //emitter.onError(new InternalErrorException());
                }
            }
        });
    }

    private String formatDate(Calendar date) {
        return DATE_FORMAT.format(date.getTime());
    }
}
