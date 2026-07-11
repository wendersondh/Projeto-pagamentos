package br.edu.ifpb.servico_pagamentos.service;

import br.edu.ifpb.servico_pagamentos.controller.request.AsaasPaymentRequest;
import br.edu.ifpb.servico_pagamentos.controller.request.AsaasWebhookRequest;
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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
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

        if (pagamentoRepository.existsByPedidoId(pedidoId)) {
            throw new RuntimeException("Este pedido já possui um pagamento gerado.");
        }

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

    public List<PagamentoResponse> listarPagamentos() {
        return pagamentoRepository.findAll()
                .stream()
                .map(PagamentoMapper::toResponse)
                .toList();
    }

    public PagamentoResponse buscarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Pagamento não encontrado."));

        return PagamentoMapper.toResponse(pagamento);
    }
    public void processarWebhook(AsaasWebhookRequest request) {

        Optional<Pagamento> pagamentoOpt = pagamentoRepository
                .findByAsaasPaymentId(request.payment().id());

        if (pagamentoOpt.isEmpty()) {
            // Pagamento não é nosso (criado fora do sistema) — ignora silenciosamente
            return;
        }

        Pagamento pagamento = pagamentoOpt.get();

        switch (request.event()) {
            case "PAYMENT_CONFIRMED", "PAYMENT_RECEIVED" -> pagamento.setStatus("CONFIRMED");
            case "PAYMENT_OVERDUE" -> pagamento.setStatus("OVERDUE");
            case "PAYMENT_REFUNDED" -> pagamento.setStatus("REFUNDED");
            default -> {
                return;
            }
        }

        pagamentoRepository.save(pagamento);
    }
}
