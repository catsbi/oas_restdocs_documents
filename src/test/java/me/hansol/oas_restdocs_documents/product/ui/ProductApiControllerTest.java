package me.hansol.oas_restdocs_documents.product.ui;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static me.hansol.oas_restdocs_documents.product.ui.ConstantForTest.SAVE_RQ_STRAWBERRY;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.hansol.oas_restdocs_documents.common.config.ConversionConfig;
import me.hansol.oas_restdocs_documents.common.converter.ConversionService;
import me.hansol.oas_restdocs_documents.product.config.ProductTestConfig;
import me.hansol.oas_restdocs_documents.product.domain.Product;
import me.hansol.oas_restdocs_documents.product.domain.ProductInMemoryRepository;
import me.hansol.oas_restdocs_documents.product.domain.ProductRepository;
import me.hansol.oas_restdocs_documents.product.domain.dto.ProductSaveRq;
import me.hansol.oas_restdocs_documents.product.service.ProductService;

@DisplayName("상품 관리 API 테스트")
@WebMvcTest(ProductApiController.class)
@Import({ConversionConfig.class, ProductTestConfig.class})
@AutoConfigureRestDocs
class ProductApiControllerTest {

	public static final String PRODUCT_API = "/api/products";
	public static final String PRODUCT_API_WITH_ID = "/api/products/{id}";
	public static final String PRODUCT_TAG = "상품 관리 API";

	private final ObjectMapper objectMapper = new ObjectMapper();
	private List<Product> products;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ConversionService conversionService;

	@BeforeEach
	void setUp() {
		products = new ArrayList<>();

		((ProductInMemoryRepository)productRepository).clear();
		products.add(productRepository.save(conversionService.convert(ConstantForTest.SAVE_RQ_APPLE, Product.class)));
		products.add(productRepository.save(conversionService.convert(ConstantForTest.SAVE_RQ_GRAPE, Product.class)));
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
					.andExpect(jsonPath("$", hasSize(2)))
					.andDo(document("상품 목록 조회",
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

		@DisplayName("저장된 상품 목록이 없을 경우")
		@Nested
		class Context_without_products {

			@BeforeEach
			void setUp() {
				for (Product product : products) {
					productRepository.delete(product);
				}
			}

			@DisplayName("200 상태 코드와, 상품 목록을 반환한다.")
			@Test
			void getFulfilledProducts() throws Exception {

				mockMvc.perform(get(PRODUCT_API).contentType(APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(0)));
			}
		}
	}

	@DisplayName("GET /product/{id} API는")
	@Nested
	class Describe_get_product {

		@DisplayName("존재하는 상품의 식별자를 조회하면")
		@Nested
		class Context_with_exists_product_id {
			private Product product;

			@BeforeEach
			void setUp() {
				product = productRepository.save(
					conversionService.convert(SAVE_RQ_STRAWBERRY, Product.class));
			}

			@DisplayName("200 상태코드와 상품 정보를 반환한다.")
			@Test
			void getProductWithValidProductId() throws Exception {
				mockMvc.perform(get(PRODUCT_API_WITH_ID, product.getId()).contentType(APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").value(product.getId()))
					.andExpect(jsonPath("$.price").value(product.getPrice()))
					.andExpect(jsonPath("$.name").value(product.getName()))
					.andDo(document("단일 상품 조회 성공",
						resource(ResourceSnippetParameters.builder()
							.tag(PRODUCT_TAG)
							.description("상품 조회 API")
							.pathParameters(parameterWithName("id").description("조회 할 상품 식별자"))
							.responseSchema(Schema.schema("상품 응답 객체 형식"))
							.responseFields(productRqDescription())
							.build())));
			}

		}

		@DisplayName("존재하지 않는 상품 정보를 조회하면")
		@Nested
		class Context_without_product_id {

			@DisplayName("404 상태 코드와, 예외 메세지를 반환한다.")
			@Test
			void getProductWithInvalidProductId() throws Exception {
				mockMvc.perform(get(PRODUCT_API_WITH_ID, 999L).contentType(APPLICATION_JSON_VALUE))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.message").value(ProductService.INVALID_PRODUCT_ID_MESSAGE))
					.andDo(document("단일 상품 조회 실패",
						resource(ResourceSnippetParameters.builder()
							.tag(PRODUCT_TAG)
							.description("존재하지 않는 상품 식별자로 상품 조회 실패")
							.pathParameters(parameterWithName("id").description("조회 할 상품 식별자"))
							.responseSchema(Schema.schema("에러 형식"))
							.responseFields(
								errorResponseDescription()
							)
							.build())));
			}
		}
	}

	@DisplayName("POST /product API는")
	@Nested
	class Describe_post_product {

		@DisplayName("상품 정보가 유효하다면")
		@Nested
		class Context_with_valid_productRq {

			@DisplayName("201 상태코드와, 저장된 상품 정보를 반환한다.")
			@Test
			void saveProductWithValidProductRq() throws Exception {
				mockMvc.perform(post(PRODUCT_API)
						.contentType(APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsBytes(SAVE_RQ_STRAWBERRY))
						.accept(APPLICATION_JSON_VALUE)
					).andExpect(status().isCreated())
					.andExpect(header().exists(HttpHeaders.LOCATION))
					.andExpect(jsonPath("$.id").hasJsonPath())
					.andExpect(jsonPath("$.price").value(SAVE_RQ_STRAWBERRY.getPrice()))
					.andExpect(jsonPath("$.name").value(SAVE_RQ_STRAWBERRY.getName()))
					.andDo(document("상품 저장 성공",
						resource(ResourceSnippetParameters.builder()
							.tag(PRODUCT_TAG)
							.description("상품 저장")
							.requestFields(
								fieldWithPath("name").description("상품 명"),
								fieldWithPath("price").description("상품 가격")
							)
							.responseSchema(Schema.schema("저장된 상품 정보"))
							.responseHeaders(
								headerWithName("location").description("생성된 상품 조회 상세정보 URL")
							)
							.responseFields(
								fieldWithPath("id").description("상품 식별자"),
								fieldWithPath("name").description("상품 명"),
								fieldWithPath("price").description("상품 가격")
							).build()
						)));
			}
		}

		@DisplayName("상품 정보(상품명)가 유효하지 않다면")
		@Nested
		class Context_with_invalid_productRq {

			@DisplayName("400 상태코드와 예외 내용을 반환합니다.")
			@ParameterizedTest
			@ArgumentsSource(InvalidProductRqProvider.class)
			void saveProductWithInvalidRq(ProductSaveRq invalidProductRq, String errorMessage) throws Exception {
				mockMvc.perform(post(PRODUCT_API)
						.contentType(APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsBytes(invalidProductRq))
						.accept(APPLICATION_JSON_VALUE)
					).andExpect(status().isBadRequest())
					.andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.toString()))
					.andExpect(jsonPath("message").value(errorMessage))
					.andDo(document("상품 저장",
						resource(ResourceSnippetParameters.builder()
							.tag(PRODUCT_TAG)
							.description("유효하지 않은 상품정보로 상품 저장")
							.requestFields(
								fieldWithPath("name").description("상품 명"),
								fieldWithPath("price").description("상품 가격")
							)
							.responseSchema(Schema.schema("에러 형식"))
							.responseFields(
								errorResponseDescription()
							).build())));
			}
		}

	}

	@NotNull
	private FieldDescriptor[] productRqDescription() {
		return new FieldDescriptor[] {
			fieldWithPath("id").description("상품 식별자"),
			fieldWithPath("price").description("상품 가격"),
			fieldWithPath("name").description("상품명")
		};
	}

	@NotNull
	private FieldDescriptor[] errorResponseDescription() {
		return new FieldDescriptor[] {
			fieldWithPath("status").description("상태 코드"),
			fieldWithPath("message").description("예외 메세지")
		};
	}

}