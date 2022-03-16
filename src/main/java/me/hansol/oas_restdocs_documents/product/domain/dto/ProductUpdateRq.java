package me.hansol.oas_restdocs_documents.product.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRq {

	@Min(0)
	private Long price;

	@NotBlank(message = "{product.name.notblank}")
	private String name;
}
