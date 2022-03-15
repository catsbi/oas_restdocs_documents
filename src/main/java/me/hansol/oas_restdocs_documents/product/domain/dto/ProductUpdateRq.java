package me.hansol.oas_restdocs_documents.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRq {
	private Long price;

	private String name;
}
