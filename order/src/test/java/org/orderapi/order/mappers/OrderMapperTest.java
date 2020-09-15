package org.orderapi.order.mappers;

import org.junit.jupiter.api.Test;
import org.orderapi.order.controllers.dto.AddressDto;
import org.orderapi.order.controllers.dto.OrderDto;
import org.orderapi.order.model.Address;
import org.orderapi.order.model.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderMapperTest {
    private OrderMapper orderMapper = new OrderMapperImpl(new AddressMapperImpl());

    @Test
    public void when_orderDomain_mapper_should_return_orderDto() {

        Order order = new Order();
        order.setId(1L);

        Address address = new Address();
        order.setAddress(address);

        OrderDto orderDto = orderMapper.toDto(order);

        assertNotNull(orderDto, "orderDto is null");
        assertNotNull(orderDto.getAddressDto(), "orderDto.getAddressDto is null");
    }

    @Test
    public void when_orderDto_mapper_should_return_orderDomain() {

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);

        AddressDto addressDto = new AddressDto();
        orderDto.setAddressDto(addressDto);

        Order order = orderMapper.toDomain(orderDto);

        assertNotNull(order, "orderDto is null");
        assertEquals(order.getId(), orderDto.getId(), "orderDto.id not equal to order.id");
        assertNotNull(order.getAddress(), "orderDto.getAddressDto is null");
    }

}