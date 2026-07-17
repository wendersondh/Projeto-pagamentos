package br.edu.ifpb.servico_pagamentos.controller;


import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPixAddressKeyRequest;
import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPixQrCodeStaticRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixAddressKeyResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixQrCodeDeleteResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixQrCodeStaticResponse;
import br.edu.ifpb.servico_pagamentos.service.AsaasPixService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pix", description = "Gestão de chaves Pix e QR Codes estáticos junto ao Asaas")
@RestController
@RequestMapping("/api/pix")
@AllArgsConstructor
public class PixController {

    private final AsaasPixService asaasPixService;

    @Operation(
            summary = "Cria uma chave Pix",
            description = "Solicita ao Asaas a criação de uma chave Pix (por padrão do tipo EVP, aleatória) para a conta do recebedor."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Chave criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping("/chaves")
    @ResponseStatus(HttpStatus.CREATED)
    public AsaasPixAddressKeyResponse criarChave(@RequestBody @Valid AsaasPixAddressKeyRequest request) {
        return asaasPixService.criarChave(request);
    }

    @Operation(
            summary = "Gera um QR Code Pix estático",
            description = "Cria um QR Code estático vinculado a uma chave Pix já existente, podendo ter valor fixo " +
                    "ou aberto e permitir um ou múltiplos pagamentos, conforme os campos enviados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "QR Code gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping("/qrcodes/estatico")
    @ResponseStatus(HttpStatus.CREATED)
    public AsaasPixQrCodeStaticResponse criarQrCodeEstatico(@RequestBody @Valid AsaasPixQrCodeStaticRequest request) {
        return asaasPixService.criarQrCodeEstatico(request);
    }

    @Operation(summary = "Remove um QR Code Pix estático pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QR Code removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "QR Code não encontrado")
    })
    @DeleteMapping("/qrcodes/estatico/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AsaasPixQrCodeDeleteResponse deletarQrCodeEstatico(
            @Parameter(description = "Id do QR Code estático no Asaas", example = "qrc_000001")
            @PathVariable String id) {
        return asaasPixService.deletarQrCodeEstatico(id);
    }
}