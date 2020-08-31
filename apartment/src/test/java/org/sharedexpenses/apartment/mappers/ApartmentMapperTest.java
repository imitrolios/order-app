package org.sharedexpenses.apartment.mappers;

import org.junit.jupiter.api.Test;
import org.sharedexpenses.apartment.controllers.dto.AddressDto;
import org.sharedexpenses.apartment.controllers.dto.ApartmentDto;
import org.sharedexpenses.apartment.model.Address;
import org.sharedexpenses.apartment.model.Apartment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApartmentMapperTest {
    private ApartmentMapper apartmentMapper = new ApartmentMapperImpl(new AddressMapperImpl());

    @Test
    public void when_apartmentDomain_mapper_should_return_apartmentDto() {

        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setFloorNumber("1");
        apartment.setBlockOfFlatsId("1");

        Address address = new Address();
        apartment.setAddress(address);

        ApartmentDto apartmentDto = apartmentMapper.toDto(apartment);

        assertNotNull(apartmentDto, "apartmentDto is null");
        assertEquals(apartmentDto.getId(), apartment.getId(), "apartmentDto.id not equal to apartment.id");
        assertEquals(apartmentDto.getBlockOfFlatsId(), apartment.getBlockOfFlatsId(),
                "apartmentDto.blockOfFlatsId not equal to apartment.blockOfFlatsId");
        assertEquals(apartmentDto.getFloorNumber(), apartment.getFloorNumber(),
                "apartmentDto.floorNumber not equal to apartment.floorNumber");
        assertNotNull(apartmentDto.getAddressDto(), "apartmentDto.getAddressDto is null");
    }

    @Test
    public void when_apartmentDto_mapper_should_return_apartmentDomain() {

        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setId(1L);
        apartmentDto.setFloorNumber("1");
        apartmentDto.setBlockOfFlatsId("1");

        AddressDto addressDto = new AddressDto();
        apartmentDto.setAddressDto(addressDto);

        Apartment apartment = apartmentMapper.toDomain(apartmentDto);

        assertNotNull(apartment, "apartmentDto is null");
        assertEquals(apartment.getId(), apartmentDto.getId(), "apartmentDto.id not equal to apartment.id");
        assertEquals(apartment.getBlockOfFlatsId(), apartmentDto.getBlockOfFlatsId(),
                "apartmentDto.blockOfFlatsId not equal to apartment.blockOfFlatsId");
        assertEquals(apartment.getFloorNumber(), apartmentDto.getFloorNumber(),
                "apartmentDto.floorNumber not equal to apartment.floorNumber");
        assertNotNull(apartment.getAddress(), "apartmentDto.getAddressDto is null");
    }

} /*address.setStreet("testStret");
        address.setPostalCode("12345");
        address.setNumber("1");
        address.setMunicipality("testMunicipality");
        address.setCountry("testCountry");
        address.setCity("testCity");
*/