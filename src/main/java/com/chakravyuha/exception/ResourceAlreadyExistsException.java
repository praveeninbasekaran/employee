package com.chakravyuha.exception;

/**
 * Exception thrown when attempting to create a resource that already exists.
 * 
 * @author Chakravyuha Team
 */
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ResourceAlreadyExistsException with the specified message.
	 * 
	 * @param message The detail message
	 */
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

	/**
	 * Constructs a new ResourceAlreadyExistsException with the specified message and cause.
	 * 
	 * @param message The detail message
	 * @param cause The cause of the exception
	 */
	public ResourceAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
