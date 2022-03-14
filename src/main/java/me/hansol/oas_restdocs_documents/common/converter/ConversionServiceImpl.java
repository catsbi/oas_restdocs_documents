package me.hansol.oas_restdocs_documents.common.converter;

import java.util.ArrayList;
import java.util.List;

import me.hansol.oas_restdocs_documents.common.exception.AlreadyExistsConverterException;
import me.hansol.oas_restdocs_documents.common.exception.NoSuchConverterException;

public class ConversionServiceImpl implements ConversionService {
	private static final List<Converter> store = new ArrayList<>();

	private ConversionServiceImpl() {
	}

	public static ConversionServiceImpl getInstance() {
		return ConversionServiceHolder.INSTANCE;
	}

	@Override
	public Converter findConverter(Class<?> type) {
		return store.stream().filter(converter -> converter.supports(type))
			.findFirst()
			.orElseThrow(() -> new NoSuchConverterException(type));
	}

	@Override
	public ConversionService addConverter(Converter converter) {
		if (store.contains(converter)) {
			throw new AlreadyExistsConverterException(converter.getClass().getName());
		}

		store.add(converter);
		return this;
	}

	@Override
	public <T> T convert(Object obj, Class<?> type) {
		return findConverter(type).convert(obj);
	}

	private static class ConversionServiceHolder {
		private static final ConversionServiceImpl INSTANCE = new ConversionServiceImpl();
	}
}
