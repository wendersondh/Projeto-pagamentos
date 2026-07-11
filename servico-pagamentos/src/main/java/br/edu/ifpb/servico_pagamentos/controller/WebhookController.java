package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.AsaasWebhookRequest;
import br.edu.ifpb.servico_pagamentos.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/payments-webhook")
@AllArgsConstructor
public class WebhookController {

    private final PagamentoService pagamentoService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Map<String, Boolean>> handleWebhook(
            @RequestBody AsaasWebhookRequest request) {

        pagamentoService.processarWebhook(request);

        return ResponseEntity.ok(Map.of("received", true));
    }
}
