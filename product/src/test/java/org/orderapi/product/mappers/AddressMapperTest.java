package org.orderapi.product.mappers;

import org.junit.jupiter.api.Test;
import org.orderapi.product.controllers.dto.AddressDto;
import org.orderapi.product.model.Address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressMapperTest {
    private AddressMapper addressMapper = new AddressMapperImpl();

    @Test
    public void when_addressDomain_mapper_should_return_addressDto() {
        Address address = new Address();
        address.setStreet("testStret");
        address.setPostalCode("12345");
        address.setNumber("1");
        address.setMunicipality("testMunicipality");
        address.setCountry("testCountry");
        address.setCity("testCity");

        AddressDto addressDto = addressMapper.toDto(address);

        assertNotNull(addressDto, "addressDto is null");
        assertEquals(addressDto.getStreet(), address.getStreet(), "addressDto.street not equal to address.street");
        assertEquals(addressDto.getPostalCode(), address.getPostalCode(),
                "addressDto.postalCode not equal to address.postalCode");
        assertEquals(addressDto.getNumber(), address.getNumber(),
                "addressDto.number not equal to address.number");
        assertEquals(addressDto.getMunicipality(), address.getMunicipality(),
                "addressDto.municipality not equal to address.municipality");

        assertEquals(addressDto.getCountry(), address.getCountry(),
                "addressDto.country not equal to address.country");
        assertEquals(addressDto.getCity(), address.getCity(),
                "addressDto.city not equal to address.city");
    }

    @Test
    public void when_addressDto_mapper_should_return_addressDomain() {

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("testStret");
        addressDto.setPostalCode("12345");
        addressDto.setNumber("1");
        addressDto.setMunicipality("testMunicipality");
        addressDto.setCountry("testCountry");
        addressDto.setCity("testCity");

        Address address = addressMapper.toDomain(addressDto);

        assertNotNull(address, "address is null");
        assertEquals(address.getStreet(), addressDto.getStreet(), "addressDto.street not equal to address.street");
        assertEquals(address.getPostalCode(), addressDto.getPostalCode(),
                "addressDto.postalCode not equal to address.postalCode");
        assertEquals(address.getNumber(), addressDto.getNumber(),
                "addressDto.number not equal to address.number");
        assertEquals(address.getMunicipality(), addressDto.getMunicipality(),
                "addressDto.municipality not equal to address.municipality");

        assertEquals(address.getCountry(), addressDto.getCountry(),
                "addressDto.country not equal to address.country");
        assertEquals(address.getCity(), addressDto.getCity(),
                "addressDto.city not equal to address.city");
    }
}
