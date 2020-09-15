package org.orderapi.order.mappers;

import org.orderapi.order.controllers.dto.AddressDto;
import org.orderapi.order.model.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressMapperImpl implements AddressMapper {
    @Override
    public AddressDto toDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(address.getStreet());
        addressDto.setNumber(address.getNumber());
        addressDto.setCountry(address.getCountry());
        addressDto.setMunicipality(address.getMunicipality());
        addressDto.setCity(address.getCity());
        addressDto.setPostalCode(address.getPostalCode());
        return addressDto;
    }

    @Override
    public Address toDomain(AddressDto addressDto) {
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setMunicipality(addressDto.getMunicipality());
        address.setNumber(addressDto.getNumber());
        address.setPostalCode(addressDto.getPostalCode());
        address.setStreet(addressDto.getStreet());
        return address;
    }
}
