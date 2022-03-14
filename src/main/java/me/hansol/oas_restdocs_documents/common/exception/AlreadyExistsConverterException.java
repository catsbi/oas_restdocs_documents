package me.hansol.oas_restdocs_documents.common.exception;

public class AlreadyExistsConverterException extends RuntimeException {
	public static final String DEFAULT_MESSAGE = "이미 등록된 컨버터입니다. converter: %s";

	public AlreadyExistsConverterException(String name) {
		super(String.format(DEFAULT_MESSAGE, name));
	}
}
