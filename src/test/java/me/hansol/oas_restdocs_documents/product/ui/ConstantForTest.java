package me.hansol.oas_restdocs_documents.product.ui;

import me.hansol.oas_restdocs_documents.product.domain.dto.ProductSaveRq;

/**
 * 테스트를 위한 더미 데이터 모음
 */
public class ConstantForTest {
	public static final ProductSaveRq SAVE_RQ_APPLE = new ProductSaveRq(1000L, "사과");
	public static final ProductSaveRq SAVE_RQ_GRAPE = new ProductSaveRq(2000L, "포도");
	public static final ProductSaveRq SAVE_RQ_STRAWBERRY = new ProductSaveRq(2500L, "딸기");
}
