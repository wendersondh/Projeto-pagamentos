package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.PedidoRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.PedidoResponse;
import br.edu.ifpb.servico_pagamentos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedidos", description = "Cadastro e consulta de pedidos")
@RestController
@RequestMapping("/api/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(summary = "Cadastra um novo pedido", description = "Cria um pedido vinculado a um cliente já cadastrado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente informado não encontrado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criarPedido(@RequestBody @Valid PedidoRequest request) {
        return pedidoService.criarPedido(request);
    }

    @Operation(summary = "Lista todos os pedidos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoResponse> listarPedido() {
        return pedidoService.listarPedidos();
    }

    @Operation(summary = "Consulta um pedido pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoResponse buscaPedido(
            @Parameter(description = "Id do pedido", example = "1")
            @PathVariable long id) {
        return pedidoService.buscaPedido(id);
    }
}
