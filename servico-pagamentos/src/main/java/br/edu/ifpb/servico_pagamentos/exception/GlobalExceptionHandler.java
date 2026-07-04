package br.edu.ifpb.servico_pagamentos.exception;

import br.edu.ifpb.servico_pagamentos.controller.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteJaCadastradoException.class)
    public ResponseEntity<ApiErrorResponse> handleClienteJaCadastrado(
            ClienteJaCadastradoException ex,
            HttpServletRequest request) {

        return construir(HttpStatus.CONFLICT, "Cliente já cadastrado", ex.getMessage(), request);
    }

    @ExceptionHandler(AsaasException.class)
    public ResponseEntity<ApiErrorResponse> handleAsaas(
            AsaasException ex,
            HttpServletRequest request) {

        return construir(HttpStatus.BAD_REQUEST, "Erro no Asaas", ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenerica(
            Exception ex,
            HttpServletRequest request) {

        log.error("Erro inesperado", ex);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.", request);
    }

    private ResponseEntity<ApiErrorResponse> construir(
            HttpStatus status, String error, String mensagem, HttpServletRequest request) {

        ApiErrorResponse erro = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(mensagem)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(erro);
    }
}