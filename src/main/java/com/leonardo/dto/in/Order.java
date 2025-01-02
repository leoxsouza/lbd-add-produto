package com.leonardo.dto.in;

import com.leonardo.service.AbstractService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RegisterForReflection
public record Order(
        String orderId,
        List<Item> items,
        String status,
        Double total,
        String timestamp,
        String observation
) {

    public Order {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order should have at least one item");
        }
        if (total == null || total <= 0) {
            throw new IllegalArgumentException("Total should be greater than zero");
        }
    }

    public static Order novo(List<Item> items, Double total, String observacao) {
        return new Order(
                UUID.randomUUID().toString(),
                items,
                "PENDING",
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
                item.get(AbstractService.ORDER_ITEMS_COL).l().stream()
                        .map(AttributeValue::m)
                        .map(m -> new Item(
                                m.get(AbstractService.ITEM_NAME_COL).s(),
                                Integer.parseInt(m.get(AbstractService.ITEM_QUANTITY_COL).n()),
                                m.get(AbstractService.ITEM_OBSERVATION_COL).s()
                        )).toList(),
                item.get(AbstractService.ORDER_STATUS_COL).s(),
                Double.parseDouble(item.get(AbstractService.ORDER_TOTAL_COL).n()),
                item.get(AbstractService.ORDER_TIMESTAMP_COL).s(),
                item.get(AbstractService.ORDER_OBSERVACAO_COL).s()
        );
    }

}
