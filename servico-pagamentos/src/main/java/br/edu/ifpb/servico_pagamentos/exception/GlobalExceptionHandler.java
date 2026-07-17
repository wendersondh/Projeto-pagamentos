package br.edu.ifpb.servico_pagamentos.exception;

import br.edu.ifpb.servico_pagamentos.controller.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> handleRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request) {

        return construir(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage(), request);
    }

    @ExceptionHandler(ConflitoDeEstadoException.class)
    public ResponseEntity<ApiErrorResponse> handleConflitoDeEstado(
            ConflitoDeEstadoException ex,
            HttpServletRequest request) {

        return construir(HttpStatus.CONFLICT, "Conflito", ex.getMessage(), request);
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