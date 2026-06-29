package br.edu.ifpb.servico_pagamentos.mapper;

import br.edu.ifpb.servico_pagamentos.controller.response.PagamentoResponse;
import br.edu.ifpb.servico_pagamentos.domain.Pagamento;

public class PagamentoMapper {

    public static PagamentoResponse toResponse(Pagamento pagamento) {
        return PagamentoResponse.builder()
                .id(pagamento.getId())
                .asaasPaymentId(pagamento.getAsaasPaymentId())
                .billingType(pagamento.getBillingType())
                .status(pagamento.getStatus())
                .value(pagamento.getValue())
                .dueDate(pagamento.getDueDate())
                .pedidoId(pagamento.getPedido().getId())
                .build();
    }

}
