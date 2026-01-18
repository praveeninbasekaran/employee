package com.chakravyuha.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Generic API response wrapper for consistent response structure.
 * 
 * @author Chakravyuha Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

	/**
	 * Response status (SUCCESS, ERROR, etc.).
	 */
	private String status;

	/**
	 * Response message.
	 */
	private String message;

	/**
	 * Response data payload.
	 */
	private T data;

	/**
	 * Timestamp of the response.
	 */
	private LocalDateTime timestamp;

	/**
	 * Creates a successful response with data.
	 * 
	 * @param <T> Type of the data
	 * @param message Success message
	 * @param data Response data
	 * @return ApiResponse instance
	 */
	public static <T> ApiResponse<T> success(String message, T data) {
		ApiResponse<T> response = new ApiResponse<>();
		response.setStatus("SUCCESS");
		response.setMessage(message);
		response.setData(data);
		response.setTimestamp(LocalDateTime.now());
		return response;
	}

	/**
	 * Creates an error response.
	 * 
	 * @param <T> Type of the data
	 * @param message Error message
	 * @return ApiResponse instance
	 */
	public static <T> ApiResponse<T> error(String message) {
		ApiResponse<T> response = new ApiResponse<>();
		response.setStatus("ERROR");
		response.setMessage(message);
		response.setTimestamp(LocalDateTime.now());
		return response;
	}
}
