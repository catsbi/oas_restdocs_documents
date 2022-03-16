package me.hansol.oas_restdocs_documents.product.ui;

import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.context.support.ResourceBundleMessageSource;

import me.hansol.oas_restdocs_documents.product.domain.dto.ProductSaveRq;

public class InvalidProductRqProvider implements ArgumentsProvider {
	public static final Locale LOCALE = Locale.KOREA;
	private static final Object[] EMPTY_ARR = new Object[] {};

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {

		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("errors");
		messageSource.setCacheSeconds(5);

		return Stream.of(
			Arguments.of(new ProductSaveRq(1500L, ""),
				messageSource.getMessage("product.name.notblank", EMPTY_ARR, LOCALE)),
			Arguments.of(new ProductSaveRq(-1000L, "수박"),
				messageSource.getMessage("product.price.min", EMPTY_ARR, LOCALE))
		);
	}
}
