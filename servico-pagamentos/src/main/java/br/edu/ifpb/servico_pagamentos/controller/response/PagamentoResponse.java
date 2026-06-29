package br.edu.ifpb.servico_pagamentos.controller.response;


import br.edu.ifpb.servico_pagamentos.enun.BillingType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PagamentoResponse {

    private Long id;
    private String asaasPaymentId;
    private BillingType billingType;
    private String status;
    private BigDecimal value;
    private LocalDate dueDate;
    private Long pedidoId;
}