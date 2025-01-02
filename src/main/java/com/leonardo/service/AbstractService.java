package com.leonardo.service;

import com.leonardo.dto.in.Order;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.Map;

public abstract class AbstractService {

    public static final String ORDER_PEDIDOID_COL = "orderId";
    public static final String ORDER_ITEMS_COL = "items";
    public static final String ORDER_STATUS_COL = "status";
    public static final String ORDER_TOTAL_COL = "total";
    public static final String ORDER_TIMESTAMP_COL = "timestamp";
    public static final String ORDER_OBSERVACAO_COL = "observation";

    public static final String ITEM_NAME_COL = "name";
    public static final String ITEM_QUANTITY_COL = "quantity";
    public static final String ITEM_OBSERVATION_COL = "observation";

    public String getTableName() {
        return "Orders";
    }

    protected ScanRequest getScanRequest() {
        return ScanRequest.builder().tableName(getTableName()).build();
    }

    protected PutItemRequest getPutItemRequest(Order order) {
        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(getItem(order))
                .build();
    }

    private Map<String, AttributeValue> getItem(Order order) {
        return Map.of(
                ORDER_PEDIDOID_COL, AttributeValue.builder().s(order.orderId()).build(),
                ORDER_STATUS_COL, AttributeValue.builder().s(order.status()).build(),
                ORDER_TOTAL_COL, AttributeValue.builder().n(order.total().toString()).build(),
                ORDER_TIMESTAMP_COL, AttributeValue.builder().s(order.timestamp()).build(),
                ORDER_OBSERVACAO_COL, AttributeValue.builder().s(order.observation()).build(),
                ORDER_ITEMS_COL, AttributeValue.builder().l(order.items().stream()
                        .map(item -> AttributeValue.builder().m(
                                Map.of(
                                        ITEM_NAME_COL, AttributeValue.builder().s(item.name()).build(),
                                        ITEM_QUANTITY_COL, AttributeValue.builder().n(item.quantity().toString()).build(),
                                        ITEM_OBSERVATION_COL, AttributeValue.builder().s(item.observation()).build()
                                )
                        ).build())
                        .toList()
                ).build()
        );
    }

}
