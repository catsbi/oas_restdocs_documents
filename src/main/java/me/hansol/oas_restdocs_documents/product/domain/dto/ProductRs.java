package me.hansol.oas_restdocs_documents.product.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.hansol.oas_restdocs_documents.product.domain.Product;

@Getter
@AllArgsConstructor
public class ProductRs {
	@ApiModelProperty("가격")
	private Long price;

	@ApiModelProperty("제품명")
	private String name;

	public static ProductRs from(Product product) {
		return new ProductRs(product.getPrice(), product.getName());
	}
}
