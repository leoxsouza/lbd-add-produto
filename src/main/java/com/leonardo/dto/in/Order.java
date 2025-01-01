package com.leonardo.dto.in;

import com.leonardo.service.AbstractService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RegisterForReflection
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

    public static Order from(Map<String, AttributeValue> item) {
        if (item == null || item.isEmpty()) {
            return null;
        }

        return new Order(
                item.get(AbstractService.ORDER_PEDIDOID_COL).s(),
                List.of(new Item("item1", 1, "obs1")), //TODO: understand how to map this items
                item.get(AbstractService.ORDER_STATUS_COL).s(),
                Double.parseDouble(item.get(AbstractService.ORDER_TOTAL_COL).n()),
                item.get(AbstractService.ORDER_TIMESTAMP_COL).s(),
                item.get(AbstractService.ORDER_OBSERVACAO_COL).s()
        );
    }

}
