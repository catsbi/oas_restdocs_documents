package me.hansol.oas_restdocs_documents.product.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.hansol.oas_restdocs_documents.common.converter.ConversionService;
import me.hansol.oas_restdocs_documents.product.domain.Product;
import me.hansol.oas_restdocs_documents.product.domain.dto.ProductRs;
import me.hansol.oas_restdocs_documents.product.domain.dto.ProductSaveRq;
import me.hansol.oas_restdocs_documents.product.domain.dto.ProductUpdateRq;
import me.hansol.oas_restdocs_documents.product.service.ProductService;

@RestController
@RequestMapping(value = "api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductApiController {
	private final ProductService productService;
	private final ConversionService conversionService;

	@GetMapping
	public ResponseEntity<List<ProductRs>> findProducts() {
		List<Product> products = productService.findProductAll();
		List<ProductRs> productRsList = products.stream().map(ProductRs::from).collect(Collectors.toList());

		return ResponseEntity.ok(productRsList);
	}

	@GetMapping("{id}")
	public ResponseEntity<ProductRs> findProduct(@PathVariable Long id) {
		Product foundProduct = productService.findProduct(id);

		return ResponseEntity.ok(ProductRs.from(foundProduct));
	}

	@PostMapping
	public ResponseEntity<ProductRs> saveProduct(@RequestBody @Valid ProductSaveRq rq) {
		Product savedProduct = productService.save(conversionService.convert(rq, Product.class));

		return ResponseEntity.created(URI.create("/api/products/" + savedProduct.getId()))
			.body(ProductRs.from(savedProduct));
	}

	@PatchMapping("{id}")
	public ResponseEntity<ProductRs> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductUpdateRq rq) {
		Product updatedProduct = productService.update(id, conversionService.convert(rq, Product.class));

		return ResponseEntity.ok(ProductRs.from(updatedProduct));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.delete(id);

		return ResponseEntity.noContent().build();
	}
}
