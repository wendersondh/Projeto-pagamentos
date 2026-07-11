package br.edu.ifpb.servico_pagamentos.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsaasPixAddressKeyResponse {

    private String id;
    private String key;
    private String type;
    private String status;
}