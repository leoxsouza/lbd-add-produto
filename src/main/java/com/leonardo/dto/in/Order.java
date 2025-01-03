package com.leonardo.dto.in;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DynamoDbBean
public class Order {
    private String orderId;
    private List<Item> items;
    private String status;
    private Double total;
    private String timestamp;
    private String observation;

    public Order() {
    }

    public Order(String orderId, List<Item> items, String status, Double total,
                 String timestamp, String observation) {
        validateOrder(items, total);
        this.orderId = orderId;
        this.items = items;
        this.status = status;
        this.total = total;
        this.timestamp = timestamp;
        this.observation = observation;
    }

    private void validateOrder(List<Item> items, Double total) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order should have at least one item");
        }
        if (total == null || total <= 0) {
            throw new IllegalArgumentException("Total should be greater than zero");
        }
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("orderId")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @DynamoDbAttribute("items")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @DynamoDbAttribute("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDbAttribute("total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @DynamoDbAttribute("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDbAttribute("observation")
    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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
}
