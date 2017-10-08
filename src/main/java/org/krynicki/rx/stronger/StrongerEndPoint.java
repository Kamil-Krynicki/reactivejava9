package org.krynicki.rx.stronger;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.krynicki.rx.stronger.services.StrongerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.krynicki.rx.stronger.responses.StrongerResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class StrongerEndPoint {

    private StrongerService strongerService;

    @Autowired
    public StrongerEndPoint(StrongerService strongerService) {
        this.strongerService = strongerService;
    }

    @GetMapping(path = "/stronger/{baseCurrency}/{counterCurrency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<StrongerResponse> getRates(
            @PathVariable final String baseCurrency,
            @PathVariable final String counterCurrency) {

        final DeferredResult<StrongerResponse> response = new DeferredResult<>();

        strongerService.isStronger(baseCurrency, counterCurrency)
                .subscribe(new SingleObserver<Boolean>() {
                    public void onSubscribe(Disposable d) {
                    }

                    public void onSuccess(Boolean result) {
                        response.setResult(new StrongerResponse(result));
                    }

                    public void onError(Throwable e) {
                        response.setErrorResult(e);
                    }
                });

        return response;
    }

}
