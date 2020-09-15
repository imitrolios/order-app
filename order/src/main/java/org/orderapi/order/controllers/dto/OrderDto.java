package org.orderapi.order.controllers.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private AddressDto addressDto;
}
