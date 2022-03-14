package me.hansol.oas_restdocs_documents.common.converter;

import java.lang.reflect.Field;

import me.hansol.oas_restdocs_documents.common.exception.NotConvertedException;
import me.hansol.oas_restdocs_documents.product.domain.Product;

/**
 * 상품 컨버터
 */
public class ProductConverter implements Converter {
	@Override
	public boolean supports(Class<?> type) {
		return type.isAssignableFrom(Product.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Product convert(Object obj) {
		Class<?> clazz = obj.getClass();

		try {
			Field priceField = clazz.getDeclaredField("price");
			priceField.setAccessible(true);
			Long price = (Long)priceField.get(obj);

			Field nameField = clazz.getDeclaredField("name");
			nameField.setAccessible(true);
			String name = (String)nameField.get(obj);

			return new Product(price, name);

		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new NotConvertedException(e.getMessage());
		}
	}
}
