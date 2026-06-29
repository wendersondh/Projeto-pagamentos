package br.edu.ifpb.servico_pagamentos.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsaasClienteResponse {

    private String id;
    private String name;
    private String cpfCnpj;
    private String email;
    private String phone;
}
