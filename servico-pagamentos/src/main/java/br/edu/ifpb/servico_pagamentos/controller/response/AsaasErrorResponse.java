package br.edu.ifpb.servico_pagamentos.controller.response;

import br.edu.ifpb.servico_pagamentos.domain.AsaasError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AsaasErrorResponse {

    private List<AsaasError> errors;
}