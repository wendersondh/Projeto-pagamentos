package br.edu.ifpb.servico_pagamentos.controller.request;

import br.edu.ifpb.servico_pagamentos.enun.PixQrCodeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Schema(description = "Dados para geração de um QR Code Pix estático")
public class AsaasPixQrCodeStaticRequest {

    @Schema(description = "Chave Pix já cadastrada no Asaas à qual o QR Code será vinculado", example = "chave-pix-000001")
    @NotBlank
    private String addressKey;

    @Schema(description = "Descrição exibida no QR Code", example = "Pagamento do pedido #123")
    private String description;

    @Schema(description = "Valor fixo do QR Code (deixe em branco para valor livre)", example = "199.90")
    private BigDecimal value;

    @Schema(description = "Formato de retorno do QR Code")
    private PixQrCodeFormat format;

    @Schema(description = "Data de expiração do QR Code")
    private OffsetDateTime expirationDate;

    @Schema(description = "Tempo de expiração em segundos, alternativa à data fixa", example = "3600")
    private Integer expirationSeconds;

    @Schema(description = "Se true, o QR Code pode ser pago mais de uma vez", example = "false")
    private Boolean allowsMultiplePayments;

    @Schema(description = "Referência externa para conciliação", example = "pedido-123")
    @Size(max = 100)
    private String externalReference;
}