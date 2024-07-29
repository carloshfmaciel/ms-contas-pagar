package br.com.desafio.exception;

public class UnexpectedException extends RuntimeException {

	private static final long serialVersionUID = 245840892790519561L;

	public UnexpectedException(Throwable cause) {
		super(cause);
	}

	public UnexpectedException(String message) {
		super(message);
	}

}
