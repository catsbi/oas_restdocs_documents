package me.hansol.oas_restdocs_documents.common.converter;

/**
 * 객체 컨버터 기능 제공
 */
public interface Converter {
	boolean supports(Class<?> type);

	<T> T convert(Object obj);
}
