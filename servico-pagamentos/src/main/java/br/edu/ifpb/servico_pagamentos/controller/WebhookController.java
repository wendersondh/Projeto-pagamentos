package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.AsaasWebhookRequest;
import br.edu.ifpb.servico_pagamentos.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private final PagamentoService pagamentoService;

    @Operation(
            summary = "Recebe notificações de eventos do Asaas",
            description = "Configurado no painel do Asaas. Não é chamado pelo front-end/Postman " +
                    "em uso normal - serve para o gateway avisar sobre confirmação, recusa ou " +
                    "estorno de um pagamento de forma assíncrona."
    )
    @ApiResponse(responseCode = "200", description = "Evento recebido e processado")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Map<String, Boolean>> handleWebhook(
            @RequestBody AsaasWebhookRequest request) {

        pagamentoService.processarWebhook(request);

        return ResponseEntity.ok(Map.of("received", true));
    }
}
