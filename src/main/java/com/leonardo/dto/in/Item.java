package com.leonardo.dto.in;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;


@DynamoDbBean
public class Item {
    private String name;
    private Integer quantity;
    private String observation;

    public Item() {
    }

    public Item(String name, Integer quantity, String observation) {
        validateItem(name, quantity);
        this.name = name;
        this.quantity = quantity;
        this.observation = observation;
    }

    private void validateItem(String name, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity should be greater than zero");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item's name should not be empty");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
