package org.krynicki.rx.rates;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import org.krynicki.rx.model.ExchangeRatesResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.krynicki.rx.rates.responses.RateResponse;
import org.krynicki.rx.rates.services.ExchangeRatesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatesEndPoint {
	
	@Autowired
	private ExchangeRatesService exchangeRatesService;
	
    @GetMapping(path = "rates/{baseCurrency}/{counterCurrency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RateResponse getRates(
    		@PathVariable final String baseCurrency,
    		@PathVariable final String counterCurrency) {
    	
    	final RateResponse response = new RateResponse();
    	final CountDownLatch outerLatch = new CountDownLatch(1);
    	
    	exchangeRatesService.getExchangeRates(baseCurrency)
    	.subscribe(new SingleObserver<ExchangeRatesResponse>() {

			public void onSubscribe(Disposable d) {}

			public void onSuccess(ExchangeRatesResponse exchangeRatesResponse) {
				
				if (exchangeRatesResponse.getRates().containsKey(counterCurrency)) {
					response.setRate(exchangeRatesResponse.getRates().get(counterCurrency));					
				} else {
		    		//async.resume(new CurrencyNotFoundException());
				}
				
				outerLatch.countDown();
			}

			public void onError(Throwable e) {
	    		//async.resume(e);
				outerLatch.countDown();
			}
		});

    	try {
    		if (!outerLatch.await(10, TimeUnit.SECONDS)) {
        		//async.resume(new InternalErrorException());
    		}
    	} catch (Exception e) {
    		//async.resume(new InternalErrorException());
    	}
    	
		return response;
    }
}
