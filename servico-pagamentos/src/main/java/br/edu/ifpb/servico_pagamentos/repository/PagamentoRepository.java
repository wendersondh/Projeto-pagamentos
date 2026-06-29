package br.edu.ifpb.servico_pagamentos.repository;

import br.edu.ifpb.servico_pagamentos.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByAsaasPaymentId(String asaasPaymentId);
}
