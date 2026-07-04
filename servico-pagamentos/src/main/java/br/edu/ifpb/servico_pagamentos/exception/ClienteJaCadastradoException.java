package br.edu.ifpb.servico_pagamentos.exception;

public class ClienteJaCadastradoException extends RuntimeException {
    public ClienteJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
