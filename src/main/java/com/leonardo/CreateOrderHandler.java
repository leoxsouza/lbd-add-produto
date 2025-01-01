package com.leonardo;

import com.leonardo.dto.in.Order;
import com.leonardo.dto.out.OutputObject;
import com.leonardo.service.CreateOrderService;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

@Named("create-order")
public class CreateOrderHandler implements RequestHandler<Order, OutputObject> {

    private final CreateOrderService service;

    public CreateOrderHandler(CreateOrderService service) {
        this.service = service;
    }

    @Override
    public OutputObject handleRequest(Order input, Context context) {
        return service.process(input).setRequestId(context.getAwsRequestId());
    }
}
