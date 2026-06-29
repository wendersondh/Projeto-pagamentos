package br.edu.ifpb.servico_pagamentos.service;

import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPaymentRequest;
import br.edu.ifpb.servico_pagamentos.controller.request.ClienteRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasClienteResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@AllArgsConstructor
public class AsaasPaymentService {

    private final RestClient asaasRestClient;

    public AsaasClienteResponse criarCliente(ClienteRequest request) {
        return asaasRestClient.post()
                .uri("/customers")
                .body(request)
                .retrieve()
                .body(AsaasClienteResponse.class);
    }

    public AsaasPaymentResponse criarCobranca(
            AsaasPaymentRequest request) {

        return asaasRestClient.post()
                .uri("/payments")
                .body(request)
                .retrieve()
                .body(AsaasPaymentResponse.class);
    }

    public AsaasPaymentResponse consultarPagamento(
            String paymentId) {

        return asaasRestClient.get()
                .uri("/payments/{id}", paymentId)
                .retrieve()
                .body(AsaasPaymentResponse.class);
    }
}
