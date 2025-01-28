package com.leonardo.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.leonardo.domain.Item;
import com.leonardo.domain.Order;
import com.leonardo.domain.enums.OrderStatusEnum;
import com.leonardo.dto.out.OutputObject;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;


@ApplicationScoped
public class CreateOrderService {

    private final DynamoDbTable<Order> orderTable;

    public CreateOrderService(final DynamoDbEnhancedClient enhancedClient) {
        this.orderTable = enhancedClient.table(Order.TABLE_NAME, TableSchema.fromClass(Order.class));
    }

    public OutputObject process(Order orderInput) {
        validateOrder(orderInput);
        orderInput.setOrderId(generateOrderId());
        orderInput.setStatus(OrderStatusEnum.PENDING);
        orderTable.putItem(orderInput);
        OutputObject out = new OutputObject();
        out.setResult("Order created with id: " + orderInput.getOrderId());
        return out;
    }

    private void validateOrder(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order should have at least one item");
        }
        if (order.getTotal() == null || order.getTotal() <= 0) {
            throw new IllegalArgumentException("Total should be greater than zero");
        }

        validateItems(order.getItems());
    }

    private void validateItems(List<Item> items) {
        for (Item item : items) {
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity should be greater than zero");
            }
            if (item.getName() == null || item.getName().isBlank()) {
                throw new IllegalArgumentException("Item's name should not be empty");
            }
        }
    }

    private Integer generateOrderId() {
        char[] numbers = "0123456789".toCharArray();
        Random random = new SecureRandom();
        return Integer.parseInt(NanoIdUtils.randomNanoId(random, numbers, 4));
    }
}
