package me.hansol.oas_restdocs_documents.product.ui;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import me.hansol.oas_restdocs_documents.common.dto.ErrorResponse;

@Slf4j
@RestControllerAdvice(basePackages = "me.hansol.oas_restdocs_documents.product")
public class ProductApiControllerAdvice {

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNoSuchElementException(RuntimeException e) {
		log.info(":::Catch Exception Handler::: NoSuchElementException: {}", e.getMessage());
		return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handlerMethodArgumentNotValidException(BindException be) {
		String errorMessage = extractBindExceptionMessages(be.getAllErrors());

		log.info(":::Catch Exception Handler::: BindException: {}", errorMessage);
		return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorMessage);
	}

	private String extractBindExceptionMessages(List<ObjectError> errors) {
		return errors.stream()
			.map(ObjectError::getDefaultMessage)
			.collect(Collectors.joining(System.lineSeparator()));
	}

}
