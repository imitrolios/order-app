package org.sharedexpenses.apartment.mappers;

import org.sharedexpenses.apartment.controllers.dto.ApartmentDto;
import org.sharedexpenses.apartment.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApartmentMapperImpl implements ApartmentMapper {

    @Autowired
    private AddressMapper addressMapper;

    public ApartmentMapperImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public ApartmentDto toDto(Apartment apartment) {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setId(apartment.getId());
        apartmentDto.setFloorNumber(apartment.getFloorNumber());
        apartmentDto.setBlockOfFlatsId(apartment.getBlockOfFlatsId());
        apartmentDto.setAddressDto(addressMapper.toDto(apartment.getAddress()));
        return apartmentDto;
    }

    @Override
    public Apartment toDomain(ApartmentDto apartmentDto) {
        Apartment apartment = new Apartment();
        apartment.setId(apartmentDto.getId());
        apartment.setFloorNumber(apartmentDto.getFloorNumber());
        apartment.setBlockOfFlatsId(apartmentDto.getBlockOfFlatsId());
        apartment.setAddress(addressMapper.toDomain(apartmentDto.getAddressDto()));
        return apartment;
    }
}
