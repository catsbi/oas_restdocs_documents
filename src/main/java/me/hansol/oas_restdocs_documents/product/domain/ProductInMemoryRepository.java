package me.hansol.oas_restdocs_documents.product.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ProductInMemoryRepository implements ProductRepository {
	private static final ProductRepository instance = new ProductInMemoryRepository();
	public static final Map<Long, Product> store = new HashMap<>();
	private static Long sequence = 0L;

	public ProductRepository getInstance() {
		return instance;
	}

	@Override
	public List<Product> findAll() {
		return new ArrayList<>(store.values());
	}

	@Override
	public Optional<Product> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Product save(Product product) {
		if (isNew(product)) {
			update(product.getId(), product);
			return product;
		}
		Long newId = generateId();
		Product newProduct = product.newProduct(newId);

		store.put(newId, newProduct);

		return newProduct;
	}

	private synchronized Long generateId() {
		return ++sequence;
	}

	private boolean isNew(Product product) {
		return Objects.nonNull(product.getId()) && findById(product.getId()).isPresent();
	}

	@Override
	public Product update(Long id, Product product) {
		Product foundProduct = findById(id).orElseThrow(IllegalArgumentException::new);
		foundProduct.update(product);

		return foundProduct;
	}

	@Override
	public Product delete(Product product) {
		return null;
	}
}
