package com.leonardo.dto.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Order(
        String pedidoId,
        List<Item> items,
        String status,
        Double total,
        String timestamp,
        String observacao
) {

    public Order {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter pelo menos um item");
        }
        if (total == null || total <= 0) {
            throw new IllegalArgumentException("Total do pedido deve ser maior que zero");
        }
    }

    public static Order novo(List<Item> items, Double total, String observacao) {
        return new Order(
                UUID.randomUUID().toString(),
                items,
                "PENDENTE",
                total,
                LocalDateTime.now().toString(),
                observacao
        );
    }
}
