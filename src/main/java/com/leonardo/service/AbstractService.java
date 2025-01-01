package com.leonardo.service;

import com.leonardo.dto.in.Item;
import com.leonardo.dto.in.Order;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractService {

    public final static String ORDER_PEDIDOID_COL = "pedidoId";
    public final static String ORDER_ITEMS_COL = "items";
    public final static String ORDER_STATUS_COL = "status";
    public final static String ORDER_TOTAL_COL = "total";
    public final static String ORDER_TIMESTAMP_COL = "timestamp";
    public final static String ORDER_OBSERVACAO_COL = "observacao";

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
                "pedidoId", AttributeValue.builder().s(order.pedidoId()).build(),
//                "items", AttributeValue.builder().l(order.items().stream().map(this::getItem).collect(Collectors.toList())).build(), TODO: understand how to map this items
                "status", AttributeValue.builder().s(order.status()).build(),
                "total", AttributeValue.builder().n(order.total().toString()).build(),
                "timestamp", AttributeValue.builder().s(order.timestamp()).build(),
                "observacao", AttributeValue.builder().s(order.observacao()).build()
        );
    }

}
