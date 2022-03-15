package me.hansol.oas_restdocs_documents.product.ui;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.hansol.oas_restdocs_documents.common.config.ConversionConfig;
import me.hansol.oas_restdocs_documents.common.converter.ConversionService;
import me.hansol.oas_restdocs_documents.product.config.ProductTestConfig;
import me.hansol.oas_restdocs_documents.product.domain.Product;
import me.hansol.oas_restdocs_documents.product.domain.ProductRepository;

@DisplayName("상품 관리 API 테스트")
@WebMvcTest(ProductApiController.class)
@Import({ConversionConfig.class, ProductTestConfig.class})
@AutoConfigureRestDocs
class ProductApiControllerTest {

	public static final String PRODUCT_API = "/api/products";
	public static final String PRODUCT_TAG = "상품 관리 API";

	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ConversionService conversionService;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();

		productRepository.save(conversionService.convert(ConstantForTest.SAVE_RQ_APPLE, Product.class));
		productRepository.save(conversionService.convert(ConstantForTest.SAVE_RQ_GRAPE, Product.class));
		productRepository.save(conversionService.convert(ConstantForTest.SAVE_RQ_STRAWBERRY, Product.class));
	}

	@DisplayName("GET /products API는")
	@Nested
	class Describe_get_products {

		@DisplayName("저장된 상품 목록이 있을 경우")
		@Nested
		class Context_with_exists_products {

			@DisplayName("200 상태 코드와, 상품 목록을 반환한다.")
			@Test
			void getFulfilledProducts() throws Exception {

				mockMvc.perform(get(PRODUCT_API).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(3)))
					.andDo(document("상품 조회",
						resource(ResourceSnippetParameters.builder()
							.tag(PRODUCT_TAG)
							.description("상품 전체 조회 API")
							.responseSchema(Schema.schema("상품 응답 객체 형식"))
							.responseFields(
								fieldWithPath("[].id").description("상품 식별자"),
								fieldWithPath("[].price").description("상품 가격"),
								fieldWithPath("[].name").description("상품명")
							)
							.build())));
			}
		}
	}

}