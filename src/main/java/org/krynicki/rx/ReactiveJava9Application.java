package org.krynicki.rx;

import org.krynicki.rx.rates.RatesEndPoint;
import org.krynicki.rx.stronger.StrongerEndPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ReactiveJava9Application {
    public static void main(String... args) {
		SpringApplication.run(ReactiveJava9Application.class, args);
	}
}
