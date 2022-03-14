package me.hansol.oas_restdocs_documents.common.exception;

public class NoSuchConverterException extends RuntimeException {
	public static final String DEFAULT_MESSAGE = "유효한 컨버터를 찾을 수 없습니다. source: [%s]";

	public NoSuchConverterException(Class<?> type) {
		super(String.format(DEFAULT_MESSAGE, type.getName()));
	}
}
