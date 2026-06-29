package br.edu.ifpb.servico_pagamentos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AsaasConfig {

    @Bean
    public RestClient asaasRestClient(
            @Value("${asaas.base-url}") String baseUrl,
            @Value("${asaas.api-key}") String apiKey
    ) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("access_token", apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}