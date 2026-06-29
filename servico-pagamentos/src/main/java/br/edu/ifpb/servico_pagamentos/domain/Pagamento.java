package br.edu.ifpb.servico_pagamentos.domain;

import br.edu.ifpb.servico_pagamentos.enun.BillingType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID da cobrança no Asaas
    @Column(unique = true)
    private String asaasPaymentId;

    @Enumerated(EnumType.STRING)
    private BillingType billingType;

    private String status;

    private BigDecimal value;

    private LocalDate dueDate;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}