package me.hansol.oas_restdocs_documents.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import me.hansol.oas_restdocs_documents.common.converter.ConversionService;
import me.hansol.oas_restdocs_documents.common.converter.ConversionServiceImpl;
import me.hansol.oas_restdocs_documents.common.converter.Converter;
import me.hansol.oas_restdocs_documents.common.converter.ProductConverter;

@Configuration
@RequiredArgsConstructor
public class ConversionConfig {

	@Bean
	public ConversionService conversionService() {
		ConversionService service = ConversionServiceImpl.getInstance();
		return service.addConverter(productConverter());
	}

	@Bean
	public Converter productConverter() {
		return new ProductConverter();
	}

}
