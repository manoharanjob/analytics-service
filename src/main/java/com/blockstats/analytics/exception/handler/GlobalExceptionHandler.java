package com.blockstats.analytics.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blockstats.analytics.exception.model.ErrorResponse;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class handle all expected and unexpected exceptions thrown by
 * application and convert that to error response and return it
 * 
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Customize the response for HttpMessageNotReadableException.
	 * <p>
	 * This method override the {@link #handleHttpMessageNotReadable} from spring
	 * global exception class and convert the exception to error response
	 * </p>
	 * 
	 * @param ex      - the exception
	 * @param headers - the headers to be written to the response
	 * @param status  - the selected response status
	 * @param request - the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Exception from handleHttpMessageNotReadable method:{}", ex.getMessage());
		Pattern pattern = Pattern.compile("\\[\"(.*?)\"\\]");
		Matcher matcher = pattern.matcher(ex.getLocalizedMessage());
		StringBuilder builder = new StringBuilder();
		while (matcher.find()) {
			builder.append(matcher.group(1)).append(".");
		}
		if(builder.length() != 0)
			builder.deleteCharAt(builder.length() - 1).append(" is invalid, ");
		return ResponseEntity.status(status)
				.body(ErrorResponse.builder()
						.error(builder.toString() + "Json parser error. Please provide valid request body/param")
						.build());
	}

	/**
	 * Customize the response for MissingServletRequestParameterException.
	 * <p>
	 * This method override the {@link #handleMissingServletRequestParameter} from spring
	 * global exception class and convert the exception to error response
	 * </p>
	 * 
	 * @param ex      - the exception
	 * @param headers - the headers to be written to the response
	 * @param status  - the selected response status
	 * @param request - the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Exception from handleMissingServletRequestParameter method:{}", ex.getMessage());
		return ResponseEntity.status(status)
				.body(ErrorResponse.builder()
						.error(ex.getParameterName() + " required request param")
						.build());
	}
	
	/**
	 * Customize the response for MethodArgumentNotValidException.
	 * <p>
	 * This method override the {@link #handleMethodArgumentNotValid} from spring
	 * global exception class and convert the exception to error response
	 * </p>
	 * 
	 * @param ex      - the exception
	 * @param headers - the headers to be written to the response
	 * @param status  - the selected response status
	 * @param request - the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Exception from handleMethodArgumentNotValid method:{}", ex.getMessage());
		List<ErrorResponse> errors = new ArrayList<>();
		BindingResult bindingResult = ex.getBindingResult();
		if (bindingResult.hasFieldErrors()) {
			errors.addAll(bindingResult.getFieldErrors()
					.stream()
					.map(error -> ErrorResponse.builder()
							.error(error.getField() + " " + error.getDefaultMessage())
							.build())
					.collect(Collectors.toList()));
		}
		if (bindingResult.hasGlobalErrors()) {
			errors.addAll(bindingResult.getGlobalErrors()
					.stream()
					.map(error -> ErrorResponse.builder()
							.error(error.getDefaultMessage())
							.build())
					.collect(Collectors.toList()));
		}
		return ResponseEntity.status(status).body(errors);
	}

	/**
	 * Customize the response for ConstraintViolationException.
	 * <p>
	 * This method called when any request body/param violate the validation policy
	 * and convert that to error response
	 * </p>
	 * 
	 * @param ex      - the exception
	 * @param request - the current request
	 * @return a {@code ResponseEntity} instance
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<ErrorResponse>> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		log.error("Exception from handleConstraintViolationException method:{}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ex.getConstraintViolations()
						.stream()
						.map(error -> ErrorResponse.builder().error(error.getMessage() + ":" + error.getMessageTemplate()).build())
						.collect(Collectors.toList()));
	}

	/**
	 * Customize the response for Exception.
	 * <p>
	 * This method called when throws any Exception from application which are not
	 * handled from global exception handler and convert that to error response
	 * </p>
	 * 
	 * @param ex      - the exception
	 * @param request - the current request
	 * @return a {@code ResponseEntity} instance
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<List<ErrorResponse>> handleGlobalException(Exception ex, WebRequest request) {
		log.error("Exception from handleGlobalException method:{}", ex.getMessage());
		List<ErrorResponse> errors = new ArrayList<>();
		errors.add(ErrorResponse.builder()
				.error(ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : "Something went wrong, please try again later")
				.build());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
	}

}
