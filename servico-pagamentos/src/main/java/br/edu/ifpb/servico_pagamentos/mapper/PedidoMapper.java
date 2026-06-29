package br.edu.ifpb.servico_pagamentos.mapper;

import br.edu.ifpb.servico_pagamentos.controller.response.PedidoResponse;
import br.edu.ifpb.servico_pagamentos.domain.Pedido;

public class PedidoMapper {

    public static PedidoResponse toResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .id(pedido.getId())
                .descricao(pedido.getDescricao())
                .valor(pedido.getValor())
                .clienteId(pedido.getCliente().getId())
                .build();
    }

}
