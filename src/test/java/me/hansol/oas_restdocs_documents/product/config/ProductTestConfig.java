package me.hansol.oas_restdocs_documents.product.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import me.hansol.oas_restdocs_documents.product.domain.ProductInMemoryRepository;
import me.hansol.oas_restdocs_documents.product.domain.ProductRepository;
import me.hansol.oas_restdocs_documents.product.service.ProductService;

@TestConfiguration
public class ProductTestConfig {
	@Bean
	public ProductService productService() {
		return new ProductService(productRepository());
	}

	@Bean
	public ProductRepository productRepository() {
		return new ProductInMemoryRepository();
	}
}
