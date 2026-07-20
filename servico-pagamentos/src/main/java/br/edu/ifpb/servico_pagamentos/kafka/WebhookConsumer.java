package br.edu.ifpb.servico_pagamentos.kafka;

import br.edu.ifpb.servico_pagamentos.controller.request.AsaasWebhookRequest;
import br.edu.ifpb.servico_pagamentos.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebhookConsumer {

    private static final Logger log = LoggerFactory.getLogger(WebhookConsumer.class);

    private final PagamentoService pagamentoService;

    @KafkaListener(topics = KafkaTopics.WEBHOOK_RECEBIDO)
    public void consumir(AsaasWebhookRequest request) {
        log.info("Processando evento de webhook: event={}, paymentId={}",
                request.event(), request.payment().id());

        pagamentoService.processarWebhook(request);
    }
}