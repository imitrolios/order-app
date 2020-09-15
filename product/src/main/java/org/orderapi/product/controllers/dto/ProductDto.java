package org.orderapi.product.controllers.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private AddressDto addressDto;
    private String numberOfFloors;
}
