package com.tuca.order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tuca.order_service.dto.OrderLineItemsDto;
import com.tuca.order_service.dto.OrderRequest;
import com.tuca.order_service.model.Order;
import com.tuca.order_service.model.OrderLineItems;
import com.tuca.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
            .stream()
            .map(this::mapToDto)
            .toList();

        order.setOrderLineItems(orderLineItemsList);
        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineDto.getPrice());
        orderLineItems.setQuantity(orderLineDto.getQuantity());
        orderLineItems.setSkuCode(orderLineDto.getSkuCode());

        return orderLineItems;
    }
}
