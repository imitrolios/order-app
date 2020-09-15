package org.orderapi.order.controllers;

import org.orderapi.common.exception.ApplicationBadInputException;
import org.orderapi.order.controllers.dto.OrderDto;
import org.orderapi.order.repositories.OrderRepository;
import org.orderapi.order.mappers.OrderMapper;
import org.orderapi.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final String ORDER_BY_ID_NOT_FOUND = "the order with the specific id does not exist";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping(path = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto fetchOrder(@PathVariable Long orderId) {
        Order order =
                orderRepository.findById(orderId).orElseThrow(
                        () -> new ApplicationBadInputException(ORDER_BY_ID_NOT_FOUND));
        return orderMapper.toDto(order);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        return orderMapper.toDto(orderRepository.saveAndFlush(orderMapper.toDomain(orderDto)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto submitOrder(@RequestBody OrderDto orderDto) {

        return orderMapper.toDto(orderRepository.saveAndFlush(orderMapper.toDomain(orderDto)));
    }
}
