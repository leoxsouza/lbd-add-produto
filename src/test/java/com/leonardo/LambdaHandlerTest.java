package com.leonardo;

import com.leonardo.dto.in.Item;
import com.leonardo.dto.in.Pedido;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class LambdaHandlerTest {

    @Test
    public void testSimpleLambdaSuccess() throws Exception {
        // you test your lambdas by invoking on http://localhost:8081
        // this works in dev mode too

        Pedido in = new Pedido(
                "123",
                List.of(new Item("item1", 1, "obs1")),
                "PENDENTE",
                100.0,
                "2021-09-01T00:00:00",
                "obs"
        );

        given()
                .contentType("application/json")
                .accept("application/json")
                .body(in)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body(containsString("item1"));
    }

}
