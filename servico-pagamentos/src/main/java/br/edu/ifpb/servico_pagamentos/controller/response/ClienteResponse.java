package br.edu.ifpb.servico_pagamentos.controller.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {

    private Long id;
    private String idAsaas;
    private String name;
    private String cpfCnpj;
    private String email;
    private String phone;
}
