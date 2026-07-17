package br.edu.ifpb.servico_pagamentos.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para criação de uma chave Pix no Asaas")
public class AsaasPixAddressKeyRequest {

    @Schema(description = "Tipo da chave Pix", example = "EVP")
    @NotBlank
    private String type = "EVP";
}