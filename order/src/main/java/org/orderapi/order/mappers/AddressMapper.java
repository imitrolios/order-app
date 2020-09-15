package org.orderapi.order.mappers;

import org.orderapi.order.controllers.dto.AddressDto;
import org.orderapi.order.model.Address;

public interface AddressMapper {
    AddressDto toDto(Address address);

    Address toDomain(AddressDto addressDto);

}
