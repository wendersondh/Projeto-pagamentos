package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.PedidoRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.PedidoResponse;
import br.edu.ifpb.servico_pagamentos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criarPedido(@RequestBody @Valid PedidoRequest request) {
        return pedidoService.criarPedido(request);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoResponse> listarPedido() {
        return pedidoService.listarPedidos();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoResponse buscaPedido(@PathVariable long id){
        return pedidoService.buscaPedido(id);
    }
}