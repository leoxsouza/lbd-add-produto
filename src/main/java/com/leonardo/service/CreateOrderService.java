package com.leonardo.service;

import com.leonardo.dto.in.Order;
import com.leonardo.dto.out.OutputObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;


@ApplicationScoped
public class CreateOrderService extends AbstractService {


    @Inject
    DynamoDbEnhancedClient enhancedClient;


    public OutputObject process(Order input) {
        DynamoDbTable<Order> orderTable = enhancedClient.table(getTableName(), TableSchema.fromClass(Order.class));
        orderTable.putItem(input);
//        var listResult = orderTable.scan().items().stream().toList();
        OutputObject out = new OutputObject();
        out.setResult("listResult.toString()");
        return out;
    }

//    public List<Order> findAll() {
//        return orderTable.scan().items().stream().toList();
//    }
}
