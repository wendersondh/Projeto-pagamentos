package br.edu.ifpb.servico_pagamentos.service;

import br.edu.ifpb.servico_pagamentos.controller.request.PedidoRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.PedidoResponse;
import br.edu.ifpb.servico_pagamentos.domain.Cliente;
import br.edu.ifpb.servico_pagamentos.domain.Pedido;
import br.edu.ifpb.servico_pagamentos.mapper.PedidoMapper;
import br.edu.ifpb.servico_pagamentos.repository.ClienteRepository;
import br.edu.ifpb.servico_pagamentos.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoResponse criarPedido(
            PedidoRequest request) {

        Cliente cliente =
                clienteRepository.findById(
                                request.getClienteId())
                        .orElseThrow(() ->
                                new br.edu.ifpb.servico_pagamentos.exception.RecursoNaoEncontradoException("Cliente de id " + request.getClienteId() + " não encontrado"));

        Pedido pedido = Pedido.builder()
                .descricao(request.getDescricao())
                .valor(request.getValor())
                .cliente(cliente)
                .build();

        pedido = pedidoRepository.save(pedido);

        return PedidoMapper.toResponse(pedido);
    }

    public List<PedidoResponse> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoMapper::toResponse)
                .toList();
    }

    public PedidoResponse buscaPedido(long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new br.edu.ifpb.servico_pagamentos.exception.RecursoNaoEncontradoException("Pedido de id " + id + " não encontrado."));

        return PedidoMapper.toResponse(pedido);
    }
}