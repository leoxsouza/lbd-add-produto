package com.leonardo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.leonardo.domain.Order;
import com.leonardo.service.ListOrderService;
import jakarta.inject.Named;

import java.util.List;

@Named("list-order")
public class ListOrderHandler implements RequestHandler<Void, List<Order>> {

    private final ListOrderService service;

    public ListOrderHandler(ListOrderService service) {
        this.service = service;
    }

    @Override
    public List<Order> handleRequest(Void orderInput, Context context) {
        return service.process();
    }
}
