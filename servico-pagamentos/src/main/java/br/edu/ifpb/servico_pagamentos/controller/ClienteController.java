package br.edu.ifpb.servico_pagamentos.controller;

import br.edu.ifpb.servico_pagamentos.controller.request.ClienteRequest;
import br.edu.ifpb.servico_pagamentos.controller.response.ClienteResponse;
import br.edu.ifpb.servico_pagamentos.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "Clientes", description = "Cadastro e consulta de clientes (sincronizados com o Asaas)")
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Cadastra um novo cliente", description = "Cria o cliente localmente e no Asaas.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Cliente já cadastrado (CPF/CNPJ duplicado)")
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public ClienteResponse criarCliente(@RequestBody @Valid ClienteRequest request) {
        return clienteService.criarCliente(request);
    }

    @Operation(summary = "Lista todos os clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponse> listarClientes() {
        return clienteService.listarClientes();
    }

    @Operation(summary = "Consulta um cliente pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponse buscarCliente(
            @Parameter(description = "Id do cliente", example = "1")
            @PathVariable Long id) {
        return clienteService.buscarCliente(id);
    }
}
