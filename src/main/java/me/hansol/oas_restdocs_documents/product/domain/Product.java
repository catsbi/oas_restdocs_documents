package me.hansol.oas_restdocs_documents.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private Long id;

	private Long price;

	private String name;

	public Product(Long price, String name) {
		this.price = price;
		this.name = name;
	}

	public Product newProduct(Long newId) {
		return new Product(newId, price, name);
	}

	public void update(Product product) {
		if (product.getPrice() != null) {
			price = product.price;
		}
		if (product.getName() != null) {
			name = product.name;
		}
	}
}
