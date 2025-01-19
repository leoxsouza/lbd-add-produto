package com.leonardo.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.leonardo.dto.in.Item;
import com.leonardo.dto.in.Order;
import com.leonardo.dto.out.OutputObject;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;


@ApplicationScoped
public class CreateOrderService {

    private final DynamoDbTable<Order> orderTable;

    public CreateOrderService(final DynamoDbEnhancedClient enhancedClient) {
        this.orderTable = enhancedClient.table("Orders", TableSchema.fromClass(Order.class));
    }

    public OutputObject process(Order input) {
        validateOrder(input);
        input.setOrderId(generateOrderId());
        orderTable.putItem(input);
        OutputObject out = new OutputObject();
        out.setResult("Order created with id: " + input.getOrderId());
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
        return Integer.parseInt(NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, numbers, 4));
    }
}
