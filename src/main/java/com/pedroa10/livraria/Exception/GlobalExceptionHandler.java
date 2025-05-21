package com.pedroa10.livraria.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	public static class ErrorResponse {
		private LocalDateTime timestamp;
		private int status;
		private String message;
		private String path;
		private Map<String, String> errors;
	
	
		public ErrorResponse(LocalDateTime timestamp, int status, String message, String path) {
			this.timestamp = timestamp;
			this.status = status;
			this.message = message;
			this.path = path;
		}
		
		public ErrorResponse(LocalDateTime timestamp, int status, String message, String path, Map<String, String> errors) {
			this.timestamp = timestamp;
			this.status = status;
			this.message = message;
			this.path = path;
			this.errors = errors;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public int getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public String getPath() {
			return path;
		}

		public Map<String, String> getErrors() {
			return errors;
		}
	}
	
	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<ErrorResponse> handleBusinessRule(EntityNotFoundException e, WebRequest request) {
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage(),
				request.getDescription(false).replace("uri=", "")
				);
				return ResponseEntity.badRequest().body(error);  
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e, WebRequest request) {
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				e.getMessage(),
				request.getDescription(false).replace("uri=", "")
				);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);  
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> handleValidation(ValidationException e, WebRequest request) {
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage(),
				request.getDescription(false).replace("uri=", "")
				);
				return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e, WebRequest request) {
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage(),
				request.getDescription(false).replace("uri=", "")
				);
				return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationSpring(MethodArgumentNotValidException e, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(error ->
			errors.put(error.getField(), error.getDefaultMessage())
		);
		
		ErrorResponse error = new ErrorResponse(
			LocalDateTime.now(),
			HttpStatus.BAD_REQUEST.value(),
			"Erro de validação",
			request.getDescription(false).replace("uri=", ""),
			errors
		);
		return ResponseEntity.badRequest().body(error);
	}
}
