package br.edu.ifpb.servico_pagamentos.controller.request;

import br.edu.ifpb.servico_pagamentos.enun.PixQrCodeFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class AsaasPixQrCodeStaticRequest {

    @NotBlank
    private String addressKey;

    private String description;

    private BigDecimal value;

    private PixQrCodeFormat format;

    private OffsetDateTime expirationDate;

    private Integer expirationSeconds;

    private Boolean allowsMultiplePayments;

    @Size(max = 100)
    private String externalReference;
}
