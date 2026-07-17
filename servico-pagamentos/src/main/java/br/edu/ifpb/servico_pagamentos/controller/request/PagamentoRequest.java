package br.edu.ifpb.servico_pagamentos.controller.request;

import br.edu.ifpb.servico_pagamentos.enun.BillingType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para geração de uma cobrança junto ao Asaas")
public class PagamentoRequest {

    @Schema(description = "Forma de cobrança", example = "PIX")
    @NotNull
    private BillingType billingType;
}
