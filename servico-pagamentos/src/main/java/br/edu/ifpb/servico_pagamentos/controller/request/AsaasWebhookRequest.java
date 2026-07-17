package br.edu.ifpb.servico_pagamentos.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload enviado pelo Asaas ao notificar um evento de pagamento")
public record AsaasWebhookRequest(

        @Schema(description = "Tipo do evento notificado", example = "PAYMENT_CONFIRMED")
        String event,

        @Schema(description = "Dados do pagamento relacionado ao evento")
        PaymentData payment
) {
    @Schema(description = "Dados resumidos do pagamento vindos do Asaas")
    public record PaymentData(

            @Schema(description = "Id do pagamento no Asaas", example = "pay_000001")
            String id,

            @Schema(description = "Status atual do pagamento no Asaas", example = "CONFIRMED")
            String status
    ) {}
}