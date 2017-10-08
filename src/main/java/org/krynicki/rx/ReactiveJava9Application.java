package org.krynicki.rx;

import org.glassfish.jersey.server.ResourceConfig;

import org.krynicki.rx.exceptions.CurrencyNotFoundMapper;
import org.krynicki.rx.exceptions.InternalErrorMapper;

import org.krynicki.rx.rates.RatesEndPoint;
import org.krynicki.rx.stronger.StrongerEndPoint;

public class ReactiveJava9Application extends ResourceConfig {

    /**
     * Register JAX-RS application component
     */
    public ReactiveJava9Application() {

    	register(RatesEndPoint.class);
    	register(StrongerEndPoint.class);
    	register(CurrencyNotFoundMapper.class);
    	register(InternalErrorMapper.class);
    }
}
