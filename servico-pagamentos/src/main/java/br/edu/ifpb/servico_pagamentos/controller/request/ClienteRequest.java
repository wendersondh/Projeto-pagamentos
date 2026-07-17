package br.edu.ifpb.servico_pagamentos.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para cadastro de um novo cliente")
public class ClienteRequest {

    @Schema(description = "Nome completo do cliente", example = "Maria da Silva")
    @NotBlank
    private String name;

    @Schema(description = "CPF ou CNPJ, apenas números", example = "12345678900")
    @NotBlank
    private String cpfCnpj;

    @Schema(description = "E-mail do cliente", example = "maria@example.com")
    @NotBlank
    private String email;

    @Schema(description = "Telefone com DDD", example = "83999998888")
    @NotNull
    private String phone;
}
