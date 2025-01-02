package com.leonardo.dto.in;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record Item(
        String name,
        Integer quantity,
        String observation
) {
    public Item {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity should be greater than zero");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item's name should not be empty");
        }
    }
}
