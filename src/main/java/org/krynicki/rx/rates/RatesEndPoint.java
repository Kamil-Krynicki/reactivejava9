package org.krynicki.rx.rates;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import org.krynicki.rx.exceptions.CurrencyNotFoundException;
import org.krynicki.rx.forex.model.ExchangeRatesResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.krynicki.rx.rates.responses.RateResponse;
import org.krynicki.rx.rates.services.ExchangeRatesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class RatesEndPoint {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @GetMapping(path = "rates/{baseCurrency}/{counterCurrency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<RateResponse> getRates(@PathVariable final String baseCurrency, @PathVariable final String counterCurrency) {

        final DeferredResult<RateResponse> deferredResult = new DeferredResult<>(10_000l);

        exchangeRatesService
                .getExchangeRates(baseCurrency)
                .subscribe(new SingleObserver<ExchangeRatesResponse>() {
                    public void onSubscribe(Disposable d) {
                    }

                    public void onSuccess(ExchangeRatesResponse exchangeRatesResponse) {
                        if (exchangeRatesResponse.hasRate(counterCurrency)) {
                            deferredResult.setResult(new RateResponse(exchangeRatesResponse.getRate(counterCurrency)));
                        } else {
                            deferredResult.setErrorResult(new CurrencyNotFoundException());
                        }
                    }

                    public void onError(Throwable e) {
                        deferredResult.setErrorResult(e);
                    }
                });

        return deferredResult;
    }
}
