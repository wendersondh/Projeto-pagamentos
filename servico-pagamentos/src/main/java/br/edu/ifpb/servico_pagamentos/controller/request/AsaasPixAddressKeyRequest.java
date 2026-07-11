package br.edu.ifpb.servico_pagamentos.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsaasPixAddressKeyRequest {

    @NotBlank
    private String type = "EVP";
}
