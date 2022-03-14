package me.hansol.oas_restdocs_documents.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String API_DOC_TITLE = "Open API(Swagger), RestDocs 조합 테스트";
	private static final String API_DOC_DESCRIPTION = "Open API(Swagger)와 RestDocs 기능을 조합하는 프로젝트";
	private static final String API_DOC_VERSION = "1.0.0";
	private static final String API_DOC_LICENSE = "Apache License Version 2.0";
	private static final String API_DOC_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";

	@Bean
	public Docket productApis() {
		String groupName = "상품 관리 APIs";

		return new Docket(DocumentationType.SWAGGER_2)
			.groupName(groupName)
			.select()
			.apis(RequestHandlerSelectors.basePackage("me.hansol.oas_restdocs_documents.product"))
			.paths(PathSelectors.any())
			.build()
			.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
			.title(API_DOC_TITLE)
			.description(API_DOC_DESCRIPTION)
			.version(API_DOC_VERSION)
			.license(API_DOC_LICENSE)
			.licenseUrl(API_DOC_LICENSE_URL)
			.build();
	}

}
