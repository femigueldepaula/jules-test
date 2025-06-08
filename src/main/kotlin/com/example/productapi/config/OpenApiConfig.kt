package com.example.productapi.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Product API")
                .version("1.0.0")
                .description("API para gerenciamento de produtos. Permite cadastrar, listar, buscar por ID e deletar produtos.")
                .termsOfService("http://example.com/terms/") // Optional
                .license(License().name("Apache 2.0").url("http://springdoc.org")) // Optional
                // .contact(Contact().name("API Support").url("http://example.com/contact").email("support@example.com")) // Optional
            )
    }
}
