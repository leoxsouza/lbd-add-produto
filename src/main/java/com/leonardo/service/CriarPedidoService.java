package com.leonardo.service;

import com.leonardo.dto.in.Pedido;
import com.leonardo.dto.out.OutputObject;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CriarPedidoService {

    public OutputObject process(Pedido input) {
        System.out.println("Processing input: " + input);
        String result = input.toString();
        OutputObject out = new OutputObject();
        out.setResult(result);
        return out;
    }
}
