package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.PagamentoRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.PagamentoResponse;
import br.edu.ifpb.servico_pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoResponse gerarPagamento(@PathVariable Long pedidoId,
            @RequestBody @Valid PagamentoRequest request) {

        return pagamentoService.gerarPagamento(
                pedidoId,
                request
        );
    }
}