package com.chakravyuha.exception;

import com.chakravyuha.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Provides centralized exception handling and consistent error responses.
 * 
 * @author Chakravyuha Team
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private static final String VALIDATION_ERROR_MESSAGE = "Validation failed";
	private static final String INTERNAL_ERROR_MESSAGE = "An internal server error occurred";

	/**
	 * Handles ResourceNotFoundException.
	 * Returns 404 Not Found status.
	 * 
	 * @param ex The exception
	 * @return ResponseEntity with error details
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.error("Resource not found: {}", ex.getMessage());
		ApiResponse<Object> response = ApiResponse.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	/**
	 * Handles ResourceAlreadyExistsException.
	 * Returns 409 Conflict status.
	 * 
	 * @param ex The exception
	 * @return ResponseEntity with error details
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Object>> handleResourceAlreadyExistsException(
			ResourceAlreadyExistsException ex) {
		log.error("Resource already exists: {}", ex.getMessage());
		ApiResponse<Object> response = ApiResponse.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	/**
	 * Handles validation errors from @Valid annotations.
	 * Returns 400 Bad Request status with field-level error details.
	 * 
	 * @param ex The validation exception
	 * @return ResponseEntity with validation error details
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		log.error("Validation error: {}", ex.getMessage());

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ApiResponse<Map<String, String>> response = ApiResponse.error(VALIDATION_ERROR_MESSAGE);
		response.setData(errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	/**
	 * Handles all other unhandled exceptions.
	 * Returns 500 Internal Server Error status.
	 * 
	 * @param ex The exception
	 * @return ResponseEntity with error details
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
		log.error("Unexpected error occurred: ", ex);
		ApiResponse<Object> response = ApiResponse.error(INTERNAL_ERROR_MESSAGE);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
