package br.edu.ifpb.servico_pagamentos.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String cpfCnpj;
    @NotBlank
    private String email;
    @NotNull
    private String phone;
}
