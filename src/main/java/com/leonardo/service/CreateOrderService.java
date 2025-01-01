package com.leonardo.service;

import com.leonardo.dto.in.Order;
import com.leonardo.dto.out.OutputObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

@ApplicationScoped
public class CreateOrderService {

    @Inject
    DynamoDbAsyncClient dynamoDbAsyncClient;

    @Inject
    DynamoDbClient dynamoDbClient;

    public OutputObject process(Order input) {
        var asyncResult = dynamoDbAsyncClient.scan(ScanRequest.builder().build());
        System.out.println(asyncResult);

        var syncResult = dynamoDbClient.scan(ScanRequest.builder().build());
        System.out.println(syncResult);

        System.out.println("Processing input: " + input);
        String result = input.toString();
        OutputObject out = new OutputObject();
        out.setResult(result);
        return out;
    }
}
