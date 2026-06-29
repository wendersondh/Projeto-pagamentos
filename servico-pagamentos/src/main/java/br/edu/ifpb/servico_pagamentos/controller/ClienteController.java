package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.ClienteRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.ClienteResponse;
import br.edu.ifpb.servico_pagamentos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(CREATED)
    public ClienteResponse criarCliente(@RequestBody @Valid ClienteRequest request) {
        return clienteService.criarCliente(request);
    }
}
