package br.edu.ifpb.servico_pagamentos.controller;


import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPixAddressKeyRequest;
import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPixQrCodeStaticRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixAddressKeyResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixQrCodeDeleteResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPixQrCodeStaticResponse;
import br.edu.ifpb.servico_pagamentos.service.AsaasPixService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pix")
@AllArgsConstructor
public class PixController {

    private final AsaasPixService asaasPixService;

    @PostMapping("/chaves")
    @ResponseStatus(HttpStatus.CREATED)
    public AsaasPixAddressKeyResponse criarChave(@RequestBody @Valid AsaasPixAddressKeyRequest request) {
        return asaasPixService.criarChave(request);
    }

    @PostMapping("/qrcodes/estatico")
    @ResponseStatus(HttpStatus.CREATED)
    public AsaasPixQrCodeStaticResponse criarQrCodeEstatico(@RequestBody @Valid AsaasPixQrCodeStaticRequest request) {
        return asaasPixService.criarQrCodeEstatico(request);
    }

    @DeleteMapping("/qrcodes/estatico/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AsaasPixQrCodeDeleteResponse deletarQrCodeEstatico(@PathVariable String id) {
        return asaasPixService.deletarQrCodeEstatico(id);
    }
}
