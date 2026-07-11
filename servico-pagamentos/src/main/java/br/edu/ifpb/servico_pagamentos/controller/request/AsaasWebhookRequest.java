package br.edu.ifpb.servico_pagamentos.controller.request;

public record AsaasWebhookRequest(
        String event,
        PaymentData payment
) {
    public record PaymentData(
            String id,
            String status
    ) {}
}