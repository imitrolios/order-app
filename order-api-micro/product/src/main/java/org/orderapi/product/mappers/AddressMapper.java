package org.orderapi.product.mappers;


import org.orderapi.product.controllers.dto.AddressDto;
import org.orderapi.product.model.Address;

public interface AddressMapper {
    AddressDto toDto(Address address);

    Address toDomain(AddressDto addressDto);

}
