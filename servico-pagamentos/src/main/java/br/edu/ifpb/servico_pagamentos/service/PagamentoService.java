package br.edu.ifpb.servico_pagamentos.service;

import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPaymentRequest;
import br.edu.ifpb.servico_pagamentos.controller.request.PagamentoRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasPaymentResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.PagamentoResponse;
import br.edu.ifpb.servico_pagamentos.domain.Cliente;
import br.edu.ifpb.servico_pagamentos.domain.Pagamento;
import br.edu.ifpb.servico_pagamentos.domain.Pedido;
import br.edu.ifpb.servico_pagamentos.enun.BillingType;
import br.edu.ifpb.servico_pagamentos.mapper.PagamentoMapper;
import br.edu.ifpb.servico_pagamentos.repository.PagamentoRepository;
import br.edu.ifpb.servico_pagamentos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Transactional
public class PagamentoService {

    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final AsaasPaymentService asaasPaymentService;

    public PagamentoResponse gerarPagamento(
            Long pedidoId,
            PagamentoRequest request) {

        Pedido pedido =
                pedidoRepository.findById(pedidoId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Pedido não encontrado"));

        Cliente cliente = pedido.getCliente();

        AsaasPaymentRequest asaasRequest =
                AsaasPaymentRequest.builder()
                        .customer(
                                cliente.getAsaasCustomerId())
                        .billingType(
                                BillingType.valueOf(request.getBillingType()
                                        .name()))
                        .value(pedido.getValor())
                        .dueDate(
                                LocalDate.now()
                                        .plusDays(5))
                        .description(
                                pedido.getDescricao())
                        .externalReference(
                                pedido.getId()
                                        .toString())
                        .build();

        AsaasPaymentResponse asaas =
                asaasPaymentService
                        .criarCobranca(asaasRequest);

        Pagamento pagamento =
                Pagamento.builder()
                        .pedido(pedido)
                        .asaasPaymentId(
                                asaas.getId())
                        .billingType(
                                request.getBillingType())
                        .status(
                                asaas.getStatus())
                        .value(
                                asaas.getValue())
                        .dueDate(
                                asaas.getDueDate())
                        .build();

        pagamento = pagamentoRepository.save(
                pagamento);

        return PagamentoMapper
                .toResponse(pagamento);
    }
}
