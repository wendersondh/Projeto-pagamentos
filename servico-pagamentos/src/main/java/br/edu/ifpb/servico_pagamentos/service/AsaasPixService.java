package br.edu.ifpb.servico_pagamentos.service;


import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPixAddressKeyRequest;
import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPixQrCodeStaticRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasErrorResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixAddressKeyResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixQrCodeDeleteResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixQrCodeStaticResponse;
import br.edu.ifpb.servico_pagamentos.exception.AsaasException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
@AllArgsConstructor
public class AsaasPixService {

    private final RestClient asaasRestClient;
    private final ObjectMapper objectMapper;

    public AsaasPixAddressKeyResponse criarChave(AsaasPixAddressKeyRequest request) {
        try {
            return asaasRestClient.post()
                    .uri("/pix/addressKeys")
                    .body(request)
                    .retrieve()
                    .body(AsaasPixAddressKeyResponse.class);

        } catch (HttpStatusCodeException ex) {
            throw new AsaasException(extrairMensagemErro(ex));

        } catch (RestClientException ex) {
            throw new AsaasException("Não foi possível se comunicar com o Asaas.");
        }
    }

    public AsaasPixQrCodeStaticResponse criarQrCodeEstatico(AsaasPixQrCodeStaticRequest request) {
        try {
            return asaasRestClient.post()
                    .uri("/pix/qrCodes/static")
                    .body(request)
                    .retrieve()
                    .body(AsaasPixQrCodeStaticResponse.class);

        } catch (HttpStatusCodeException ex) {
            throw new AsaasException(extrairMensagemErro(ex));

        } catch (RestClientException ex) {
            throw new AsaasException("Não foi possível se comunicar com o Asaas.");
        }
    }

    public AsaasPixQrCodeDeleteResponse deletarQrCodeEstatico(String id) {
        try {
            return asaasRestClient.delete()
                    .uri("/pix/qrCodes/static/{id}", id)
                    .retrieve()
                    .body(AsaasPixQrCodeDeleteResponse.class);

        } catch (HttpStatusCodeException ex) {
            throw new AsaasException(extrairMensagemErro(ex));

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
}