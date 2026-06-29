package br.edu.ifpb.servico_pagamentos.repository;

import br.edu.ifpb.servico_pagamentos.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpfCnpj(String cpfCnpj);

    boolean existsByCpfCnpj(String cpfCnpj);

    Optional<Cliente> findByAsaasCustomerId(String asaasCustomerId);
}
