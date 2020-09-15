package org.orderapi.order.mappers;

import org.orderapi.order.controllers.dto.OrderDto;
import org.orderapi.order.model.Order;

public interface OrderMapper {

    OrderDto toDto(Order order);

    Order toDomain(OrderDto orderDto);

}
