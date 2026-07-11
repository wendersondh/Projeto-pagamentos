package br.edu.ifpb.servico_pagamentos.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AsaasPixQrCodeStaticResponse {

    private String id;
    private String payload;
    private String encodedImage;
    private String description;
    private BigDecimal value;
    private Boolean allowsMultiplePayments;
    private String externalReference;
}
