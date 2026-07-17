package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.PagamentoRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.PagamentoResponse;
import br.edu.ifpb.servico_pagamentos.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pagamentos", description = "Geração e consulta de cobranças junto ao gateway Asaas")
@RestController
@RequestMapping("/api/pagamentos")
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Operation(
            summary = "Gera uma cobrança para um pedido existente",
            description = "Cria a cobrança no Asaas (PIX ou boleto, conforme billingType) " +
                    "vinculada a um pedido já cadastrado, e persiste o pagamento localmente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pagamento gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = PagamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "409", description = "Pedido já possui um pagamento gerado")
    })
    @PostMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoResponse gerarPagamento(
            @Parameter(description = "Id do pedido para o qual a cobrança será gerada", example = "1")
            @PathVariable Long pedidoId,
            @RequestBody @Valid PagamentoRequest request) {

        return pagamentoService.gerarPagamento(pedidoId, request);
    }

    @Operation(summary = "Lista o histórico de todos os pagamentos gerados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PagamentoResponse> listarPagamentos() {
        return pagamentoService.listarPagamentos();
    }

    @Operation(summary = "Consulta o status atual de um pagamento pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PagamentoResponse buscarPagamento(
            @Parameter(description = "Id interno do pagamento", example = "1")
            @PathVariable Long id) {
        return pagamentoService.buscarPagamento(id);
    }
}
