package br.edu.ifpb.servico_pagamentos.controller.response;

import br.edu.ifpb.servico_pagamentos.enun.BillingType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsaasPaymentResponse {

    private String id;
    private String customer;
    private BillingType billingType;
    private BigDecimal value;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private String description;
}