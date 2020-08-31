package org.sharedexpenses.apartment.mappers;

import org.sharedexpenses.apartment.controllers.dto.ApartmentDto;
import org.sharedexpenses.apartment.model.Apartment;

public interface ApartmentMapper {

    ApartmentDto toDto(Apartment apartment);

    Apartment toDomain(ApartmentDto apartmentDto);

}
