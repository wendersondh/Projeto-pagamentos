package br.edu.ifpb.servico_pagamentos.service;

import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPaymentRequest;
import br.edu.ifpb.servico_pagamentos.controller.request.ClienteRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasClienteResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasErrorResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPaymentResponse;
import br.edu.ifpb.servico_pagamentos.exception.AsaasException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestClientException;

@Service
@AllArgsConstructor
public class AsaasPaymentService {

    private final RestClient asaasRestClient;
    private final ObjectMapper objectMapper;

    public AsaasClienteResponse criarCliente(ClienteRequest request) {
        try {
            return asaasRestClient.post()
                    .uri("/customers")
                    .body(request)
                    .retrieve()
                    .body(AsaasClienteResponse.class);

        } catch (HttpStatusCodeException ex) {
            String mensagem = extrairMensagemErro(ex);
            throw new AsaasException(mensagem);

        } catch (RestClientException ex) {
            throw new AsaasException("Não foi possível se comunicar com o Asaas.");
        }
    }

    private String extrairMensagemErro(HttpStatusCodeException ex) {
        try {
            AsaasErrorResponse erro = objectMapper.readValue(
                    ex.getResponseBodyAsString(), AsaasErrorResponse.class);

            return erro.getErrors().get(0).getDescription();

        } catch (Exception e) {
            return "Erro ao processar a solicitação no Asaas.";
        }
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
