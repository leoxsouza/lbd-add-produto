package com.leonardo.service;

import com.leonardo.dto.in.Order;
import com.leonardo.dto.out.OutputObject;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;

@ApplicationScoped
public class CreateOrderService extends AbstractService {

    private final DynamoDbClient dynamoDbClient;

    public CreateOrderService(final DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public OutputObject process(Order input) {
        var result = this.findAll();
        System.out.println(result);
        OutputObject out = new OutputObject();
        out.setResult(result.toString());
        return out;
    }

    private List<Order> findAll() {
        return dynamoDbClient.scanPaginator(getScanRequest()).items().stream()
                .map(Order::from)
                .toList();
    }
}
