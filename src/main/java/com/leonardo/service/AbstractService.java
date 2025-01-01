package com.leonardo.service;

import com.leonardo.dto.in.Order;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.Map;

public abstract class AbstractService {

    public static final String ORDER_PEDIDOID_COL = "pedidoId";
    public static final String ORDER_ITEMS_COL = "items";
    public static final String ORDER_STATUS_COL = "status";
    public static final String ORDER_TOTAL_COL = "total";
    public static final String ORDER_TIMESTAMP_COL = "timestamp";
    public static final String ORDER_OBSERVACAO_COL = "observacao";

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
                ORDER_PEDIDOID_COL, AttributeValue.builder().s(order.pedidoId()).build(),
//                "items", AttributeValue.builder().l(order.items().stream().map(this::getItem).collect(Collectors.toList())).build(), TODO: understand how to map this items
                ORDER_STATUS_COL, AttributeValue.builder().s(order.status()).build(),
                ORDER_TOTAL_COL, AttributeValue.builder().n(order.total().toString()).build(),
                ORDER_TIMESTAMP_COL, AttributeValue.builder().s(order.timestamp()).build(),
                ORDER_OBSERVACAO_COL, AttributeValue.builder().s(order.observacao()).build()
        );
    }

}
