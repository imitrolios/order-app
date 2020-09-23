package org.orderapi.order.mappers;

import org.orderapi.order.controllers.dto.OrderDto;
import org.orderapi.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private AddressMapper addressMapper;

    public OrderMapperImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAddressDto(addressMapper.toDto(order.getAddress()));
        return orderDto;
    }

    @Override
    public Order toDomain(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setAddress(addressMapper.toDomain(orderDto.getAddressDto()));
        return order;
    }
}
