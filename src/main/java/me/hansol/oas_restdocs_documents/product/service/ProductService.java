package me.hansol.oas_restdocs_documents.product.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hansol.oas_restdocs_documents.product.domain.Product;
import me.hansol.oas_restdocs_documents.product.domain.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
	public static final String INVALID_PRODUCT_ID_MESSAGE = "존재하지 않는 상품 아이디 입니다.";
	private final ProductRepository productRepository;

	public Product findProduct(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException(INVALID_PRODUCT_ID_MESSAGE));
	}

	public Product save(Product product) {
		return productRepository.save(product);
	}

	public Product update(Long id, Product product) {
		return productRepository.update(id, product);
	}

	public void delete(Long id) {
		productRepository.delete(id);
	}

	public List<Product> findProductAll() {
		return productRepository.findAll();
	}
}
