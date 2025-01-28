package com.leonardo.service;

import com.leonardo.domain.Order;
import com.leonardo.domain.enums.OrderStatusEnum;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;


@ApplicationScoped
public class ListOrderService {

    private final DynamoDbTable<Order> orderTable;

    public ListOrderService(final DynamoDbEnhancedClient enhancedClient) {
        this.orderTable = enhancedClient.table(Order.TABLE_NAME, TableSchema.fromClass(Order.class));
    }

    public List<Order> process() {
        QueryConditional conditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(OrderStatusEnum.PENDING.getStatus())
                .build());
        var result = orderTable.index(Order.STATUS_TIMESTAMP_INDEX).query(r -> r.queryConditional(conditional));
        return result.stream().flatMap(page -> page.items().stream()).toList();
    }

}
