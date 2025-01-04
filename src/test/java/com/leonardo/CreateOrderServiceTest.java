package com.leonardo;


import com.leonardo.dto.in.Item;
import com.leonardo.dto.in.Order;
import com.leonardo.dto.out.OutputObject;
import com.leonardo.service.CreateOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderServiceTest {

    @Mock
    private DynamoDbTable<Order> orderTable;

    @Mock
    private DynamoDbEnhancedClient enhancedClient;

    private CreateOrderService service;

    @BeforeEach
    void setUp() {
        when(enhancedClient.table(anyString(), any(TableSchema.class))).thenReturn(orderTable);
        service = new CreateOrderService(enhancedClient);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Arrange
        Order input = new Order();
        input.setTotal(100.0);
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("Product");
        item.setQuantity(1);
        items.add(item);
        input.setItems(items);

        // Act
        OutputObject result = service.process(input);

        // Assert
        verify(orderTable).putItem(input);
        assertNotNull(input.getOrderId());
        assertTrue(result.getResult().contains("Order created with id:"));
    }

    @Test
    void shouldThrowExceptionWhenOrderHasNoItems() {
        // Arrange
        Order input = new Order();
        input.setTotal(100.0);
        input.setItems(new ArrayList<>());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.process(input));
    }

    @Test
    void shouldThrowExceptionWhenItemNameIsEmpty() {
        // Arrange
        Order input = new Order();
        input.setTotal(100.0);
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("");
        item.setQuantity(1);
        items.add(item);
        input.setItems(items);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.process(input));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0})
    void shouldThrowExceptionWhenTotalIsInvalid(Double total) {
        // Arrange
        Order input = new Order();
        input.setTotal(total);
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("Product");
        item.setQuantity(1);
        items.add(item);
        input.setItems(items);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.process(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldThrowExceptionWhenItemQuantityIsInvalid(Integer quantity) {
        // Arrange
        Order input = new Order();
        input.setTotal(100.0);
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("Product");
        item.setQuantity(quantity);
        items.add(item);
        input.setItems(items);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.process(input));
    }

}
