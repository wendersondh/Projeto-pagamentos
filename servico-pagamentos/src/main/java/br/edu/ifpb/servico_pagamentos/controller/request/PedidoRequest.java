package br.edu.ifpb.servico_pagamentos.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Dados para cadastro de um novo pedido")
public class PedidoRequest {

    @Schema(description = "Descrição do pedido", example = "Assinatura mensal do plano Pro")
    @NotBlank
    private String descricao;

    @Schema(description = "Valor do pedido em reais", example = "199.90")
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal valor;

    @Schema(description = "Id do cliente já cadastrado ao qual o pedido pertence", example = "1")
    @NotNull
    private Long clienteId;
}
