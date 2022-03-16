package me.hansol.oas_restdocs_documents.product.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class ProductInMemoryRepository implements ProductRepository {
	public static final String INVALID_PRODUCT_PARAMETER = "유효하지 않은 상품 정보입니다.";

	private static final ProductRepository instance = new ProductInMemoryRepository();
	private static final Map<Long, Product> store = new ConcurrentHashMap<>();
	private static Long sequence = 0L;

	public ProductRepository getInstance() {
		return instance;
	}

	public synchronized void clear() {
		sequence = 0L;
		store.clear();
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
		if (Objects.isNull(product) || Objects.isNull(product.getId())) {
			throw new IllegalArgumentException(INVALID_PRODUCT_PARAMETER);
		}
		return store.remove(product.getId());
	}
}
