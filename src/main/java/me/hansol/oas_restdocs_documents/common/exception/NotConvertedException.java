package me.hansol.oas_restdocs_documents.common.exception;

public class NotConvertedException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "도메인으로 변환이 실패했습니다.";

	public NotConvertedException() {
		this(DEFAULT_MESSAGE);
	}

	public NotConvertedException(String message) {
		super(message);
	}
}
