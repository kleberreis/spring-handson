package br.com.eng.handson2.exception;

public class OrderAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public OrderAlreadyExistsException(String message) {
		super(message);
	}

}
