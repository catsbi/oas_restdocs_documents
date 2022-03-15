package me.hansol.oas_restdocs_documents.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.hansol.oas_restdocs_documents.product.domain.Product;

@Getter
@AllArgsConstructor
public class ProductRs {

	private Long id;

	private Long price;

	private String name;

	public static ProductRs from(Product product) {
		return new ProductRs(product.getId(), product.getPrice(), product.getName());
	}
}
