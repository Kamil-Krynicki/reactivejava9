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

@RestController
public class StrongerEndPoint {

	@Autowired
	private StrongerService strongerService;

    @GetMapping(path = "/stronger/{baseCurrency}/{counterCurrency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StrongerResponse getRates(
    		@PathVariable final String baseCurrency,
    		@PathVariable final String counterCurrency) {
    	
    	final StrongerResponse response = new StrongerResponse();
    	
    	final CountDownLatch outerLatch = new CountDownLatch(1);
    	
    	strongerService.isStronger(baseCurrency, counterCurrency).subscribe(new SingleObserver<Boolean>() {

			public void onSubscribe(Disposable d) {}

			public void onSuccess(Boolean result) {
				response.setStronger(result);
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
