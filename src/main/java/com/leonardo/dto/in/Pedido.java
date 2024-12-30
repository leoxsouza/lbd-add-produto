package com.leonardo.dto.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Pedido(
        String pedidoId,
        List<Item> items,
        String status,
        Double total,
        String timestamp,
        String observacao
) {
    // Construtor compacto com validações
    public Pedido {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter pelo menos um item");
        }
        if (total == null || total <= 0) {
            throw new IllegalArgumentException("Total do pedido deve ser maior que zero");
        }
    }

    // Factory method para criar pedido novo
    public static Pedido novo(List<Item> items, Double total, String observacao) {
        return new Pedido(
                UUID.randomUUID().toString(),
                items,
                "PENDENTE",
                total,
                LocalDateTime.now().toString(),
                observacao
        );
    }
}
