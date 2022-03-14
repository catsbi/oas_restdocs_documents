package me.hansol.oas_restdocs_documents.product.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveRq {

	@ApiModelProperty("가격")
	private Long price;

	@ApiModelProperty("제품명")
	private String name;
}
