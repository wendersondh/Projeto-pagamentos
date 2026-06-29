package br.edu.ifpb.servico_pagamentos.service;

import br.edu.ifpb.servico_pagamentos.controller.request.ClienteRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.AsaasClienteResponse;
import br.edu.ifpb.servico_pagamentos.controller.response.ClienteResponse;
import br.edu.ifpb.servico_pagamentos.domain.Cliente;
import br.edu.ifpb.servico_pagamentos.mapper.ClienteMapper;
import br.edu.ifpb.servico_pagamentos.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final AsaasPaymentService asaasPaymentService;

    public ClienteResponse criarCliente(
            ClienteRequest request) {

        if (clienteRepository.existsByCpfCnpj(
                request.getCpfCnpj())) {
            throw new RuntimeException(
                    "Cliente já cadastrado.");
        }

        AsaasClienteResponse asaas =
                asaasPaymentService.criarCliente(request);

        Cliente cliente = Cliente.builder()
                .name(request.getName())
                .cpfCnpj(request.getCpfCnpj())
                .email(request.getEmail())
                .phone(request.getPhone())
                .asaasCustomerId(asaas.getId())
                .build();

        cliente = clienteRepository.save(cliente);

        return ClienteMapper.toResponse(cliente);
    }
}
