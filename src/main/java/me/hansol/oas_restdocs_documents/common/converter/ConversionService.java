package me.hansol.oas_restdocs_documents.common.converter;

public interface ConversionService {
	Converter findConverter(Class<?> type);

	ConversionService addConverter(Converter converter);

	<T> T convert(Object obj, Class<?> type);
}
