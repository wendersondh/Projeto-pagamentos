package br.edu.ifpb.servico_pagamentos.mapper;

import br.edu.ifpb.servico_pagamentos.controller.response.ClienteResponse;
import br.edu.ifpb.servico_pagamentos.domain.Cliente;

public class ClienteMapper {

    public static ClienteResponse toResponse(Cliente cliente){
        return ClienteResponse.builder()
                .id(cliente.getId())
                .idAsaas(cliente.getAsaasCustomerId())
                .name(cliente.getName())
                .email(cliente.getEmail())
                .phone(cliente.getPhone())
                .cpfCnpj(cliente.getCpfCnpj())
                .build();

    }



}
