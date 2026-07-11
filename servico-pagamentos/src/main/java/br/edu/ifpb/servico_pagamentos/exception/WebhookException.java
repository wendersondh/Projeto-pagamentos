package br.edu.ifpb.servico_pagamentos.exception;

public class WebhookException extends RuntimeException {
    public WebhookException(String mensagem) {
        super(mensagem);
    }
}
