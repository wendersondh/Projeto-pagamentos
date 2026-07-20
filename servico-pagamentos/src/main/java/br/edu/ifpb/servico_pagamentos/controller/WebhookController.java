package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.AsaasWebhookRequest;
import br.edu.ifpb.servico_pagamentos.kafka.KafkaTopics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Webhook Asaas", description = "Endpoint chamado pelo Asaas para notificar mudanças de status de pagamento")
@RestController
@RequestMapping("/payments-webhook")
@AllArgsConstructor
public class WebhookController {

    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Operation(
            summary = "Recebe notificações de eventos do Asaas",
            description = "Configurado no painel do Asaas. Não é chamado pelo front-end/Postman " +
                    "em uso normal - serve para o gateway avisar sobre confirmação, recusa ou " +
                    "estorno de um pagamento de forma assíncrona. O evento é publicado no Kafka " +
                    "e processado de forma assíncrona por um consumer."
    )
    @ApiResponse(responseCode = "200", description = "Evento recebido e publicado para processamento")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Map<String, Boolean>> handleWebhook(
            @RequestBody AsaasWebhookRequest request) {

        log.info("Webhook recebido do Asaas: event={}, paymentId={}",
                request.event(), request.payment().id());

        kafkaTemplate.send(KafkaTopics.WEBHOOK_RECEBIDO, request.payment().id(), request);

        return ResponseEntity.ok(Map.of("received", true));
    }
}
