package org.sharedexpenses.apartment.mappers;

import org.sharedexpenses.apartment.controllers.dto.AddressDto;
import org.sharedexpenses.apartment.model.Address;

public interface AddressMapper {
    AddressDto toDto(Address address);

    Address toDomain(AddressDto addressDto);

}
