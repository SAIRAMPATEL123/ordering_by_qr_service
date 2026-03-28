package com.user.visitorbackend.common;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ApiErrorResponse is a record that represents a standardized API error response structure.
 * 
 * This record encapsulates error details returned by the API when a request fails or 
 * encounters validation issues. It provides a consistent error response format across 
 * the application.
 * 
 * Fields:
 * @param timestamp The date and time when the error occurred, represented as a LocalDateTime object
 * @param status The HTTP status code associated with the error (e.g., 400, 404, 500)
 * @param message A human-readable error message describing what went wrong
 * @param errors A list of detailed error messages or validation error descriptions
 * 
 * Usage:
 * This record is typically used in exception handlers or error controllers to return
 * a uniform JSON response when errors occur, making it easier for API consumers to 
 * handle and understand error responses.
 * 
 * Example response:
 * {
 *   "timestamp": "2024-01-15T10:30:45",
 *   "status": 400,
 *   "message": "Validation failed",
 *   "errors": ["Email is required", "Password must be at least 8 characters"]
 * }
 */
public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        List<String> errors
) {
}
