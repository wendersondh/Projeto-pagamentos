package br.edu.ifpb.servico_pagamentos.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PedidoResponse {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private Long clienteId;
}