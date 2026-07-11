package br.edu.ifpb.servico_pagamentos.controller.request;

import br.edu.ifpb.servico_pagamentos.enun.BillingType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsaasPaymentRequest {

    private String customer;
    private BillingType billingType;
    private BigDecimal value;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private String description;
    private String externalReference;
}
