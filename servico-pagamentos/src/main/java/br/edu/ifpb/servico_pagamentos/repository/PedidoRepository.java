package br.edu.ifpb.servico_pagamentos.repository;

import br.edu.ifpb.servico_pagamentos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
