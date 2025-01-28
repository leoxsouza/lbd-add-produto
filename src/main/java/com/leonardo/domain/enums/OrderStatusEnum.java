package com.leonardo.domain.enums;

public enum OrderStatusEnum {

    PENDING("PENDING"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED");

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
