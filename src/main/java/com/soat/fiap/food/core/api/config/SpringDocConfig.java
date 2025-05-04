package com.soat.fiap.food.core.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do SpringDoc para documentação da API
 */
@Configuration
public class SpringDocConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Food Core API")
                        .description("API para sistema de pedidos de fast food com arquitetura hexagonal")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe FIAP/SOAT")
                                .email("suporte@foodcoreapi.com")
                                .url("https://github.com/fiap-soat/food-core-api"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url(contextPath)
                                .description("API Server")
                ));
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("produtos")
                .packagesToScan("com.soat.fiap.food.core.api.product")
                .pathsToMatch("/api/products/**", "/api/categories/**")
                .build();
    }

    @Bean
    public GroupedOpenApi customerApi() {
        return GroupedOpenApi.builder()
                .group("clientes")
                .packagesToScan("com.soat.fiap.food.core.api.customer")
                .pathsToMatch("/api/customers/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("pedidos")
                .packagesToScan("com.soat.fiap.food.core.api.order")
                .pathsToMatch("/api/orders/**")
                .build();
    }

    @Bean
    public GroupedOpenApi paymentApi() {
        return GroupedOpenApi.builder()
                .group("pagamentos")
                .packagesToScan("com.soat.fiap.food.core.api.payment")
                .pathsToMatch("/api/payments/**")
                .build();
    }
} 