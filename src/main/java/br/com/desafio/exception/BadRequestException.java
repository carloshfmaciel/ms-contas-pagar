package br.com.desafio.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 245840892790519561L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

}
