package com.leonardo;

import com.leonardo.dto.in.Pedido;
import com.leonardo.dto.out.OutputObject;
import com.leonardo.service.CriarPedidoService;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

@Named("criar-pedido")
public class CriarPedidoHandler implements RequestHandler<Pedido, OutputObject> {

    @Inject
    CriarPedidoService service;

    @Override
    public OutputObject handleRequest(Pedido input, Context context) {
        return service.process(input).setRequestId(context.getAwsRequestId());
    }
}
