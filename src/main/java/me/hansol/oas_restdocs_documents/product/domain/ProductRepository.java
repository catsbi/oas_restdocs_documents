package me.hansol.oas_restdocs_documents.product.domain;

import java.util.List;
import java.util.Optional;

/**
 * 상품 저장소
 */
public interface ProductRepository {

	List<Product> findAll();

	Optional<Product> findById(Long id);

	Product save(Product product);

	Product update(Long id, Product product);

	Product delete(Product product);

	default Product delete(Long id) {
		return findById(id)
			.map(this::delete)
			.orElseThrow(IllegalAccessError::new);
	}
}
