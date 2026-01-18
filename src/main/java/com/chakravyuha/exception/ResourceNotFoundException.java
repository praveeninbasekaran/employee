package com.chakravyuha.exception;

/**
 * Exception thrown when a requested resource is not found.
 * 
 * @author Chakravyuha Team
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ResourceNotFoundException with the specified message.
	 * 
	 * @param message The detail message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructs a new ResourceNotFoundException with the specified message and cause.
	 * 
	 * @param message The detail message
	 * @param cause The cause of the exception
	 */
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
