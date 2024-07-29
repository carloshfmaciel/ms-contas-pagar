package br.com.desafio.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3651057336733571847L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}
}
