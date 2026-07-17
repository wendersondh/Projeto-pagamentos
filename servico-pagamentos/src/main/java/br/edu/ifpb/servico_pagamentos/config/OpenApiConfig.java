package br.edu.ifpb.servico_pagamentos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pagamentos")
                        .description("""
                                Integração de aplicação Java Spring com o gateway de pagamento Asaas.
                                Permite cadastrar clientes e pedidos, gerar cobranças, consultar o
                                status de pagamentos e receber notificações via webhook.
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Equipe do projeto")
                                .email("equipe@exemplo.com"))
                        .license(new License()
                                .name("Uso acadêmico - IFPB")));
    }
}
