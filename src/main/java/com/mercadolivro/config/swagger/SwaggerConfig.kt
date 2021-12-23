package com.mercadolivro.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@Profile("!prod")
class SwaggerConfig {

    @Bean
    fun api() = Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(PathSelectors.any())
        .apis(RequestHandlerSelectors.basePackage("com.mercadolivro.controller  "))
        .build()

        .apiInfo(
            ApiInfoBuilder()
                .title("mercado-livro-api")
                .description("Api do mercado livro")
                .build()
        )
}