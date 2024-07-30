package br.com.desafio.rest.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.desafio.exception.BadRequestException;
import br.com.desafio.exception.InvalidParameterException;
import br.com.desafio.exception.NotFoundException;
import br.com.desafio.exception.UnexpectedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
				BadRequestException.class.getName(), ex.getMessage()));
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException ex,
			HttpServletRequest request) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(), InvalidParameterException.class.getName(), ex.getMessage()));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorResponse(HttpStatus.NOT_FOUND.value(), NotFoundException.class.getName(), ex.getMessage()));
	}

	@ExceptionHandler(UnexpectedException.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedException(UnexpectedException ex, HttpServletRequest request) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), UnexpectedException.class.getName(), ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), Exception.class.getName(), ex.getMessage()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity handle(ConstraintViolationException constraintViolationException) {
		Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
		String errorMessage = "";
		if (!violations.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			violations.forEach(violation -> builder.append(" " + violation.getMessage()));
			errorMessage = builder.toString();
		} else {
			errorMessage = "ConstraintViolationException occured.";
		}
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	
}
