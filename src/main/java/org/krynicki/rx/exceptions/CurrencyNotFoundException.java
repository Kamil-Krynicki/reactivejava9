package org.krynicki.rx.exceptions;

public class CurrencyNotFoundException extends Exception {

	public CurrencyNotFoundException() {
		
		super("Currency not supported");
	}
}
