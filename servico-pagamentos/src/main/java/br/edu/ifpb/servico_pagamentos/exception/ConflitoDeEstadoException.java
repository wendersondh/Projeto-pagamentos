package br.edu.ifpb.servico_pagamentos.exception;

public class ConflitoDeEstadoException extends RuntimeException {
    public ConflitoDeEstadoException(String mensagem) {
        super(mensagem);
    }
}