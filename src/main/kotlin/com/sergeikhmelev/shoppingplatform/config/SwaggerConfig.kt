package com.sergeikhmelev.shoppingplatform.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

	@Bean
	fun apiInfo(): OpenAPI =
		OpenAPI().info(Info().title("Shopping Platform"))

	@Bean
	fun httpApi(): GroupedOpenApi {
		return GroupedOpenApi.builder()
			.group("http")
			.pathsToMatch("/**")
			.build()
	}
}
