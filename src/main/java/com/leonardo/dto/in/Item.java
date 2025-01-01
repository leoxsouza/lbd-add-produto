package com.leonardo.dto.in;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record Item(
        String nome,
        Integer quantidade,
        String observacao
) {
    public Item {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do item é obrigatório");
        }
    }
}
