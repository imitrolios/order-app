package org.sharedexpenses.apartment.repositories;

import org.junit.jupiter.api.Test;
import org.sharedexpenses.apartment.model.Address;
import org.sharedexpenses.apartment.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ApartmentRepositoryTest {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    public void testCreateApartment() {
        Apartment apartment = createApartmentWithTestValues();
        apartment.setAddress(createAddressWithTestValues());
        apartmentRepository.save(apartment);

        Assert.isTrue(apartmentRepository.findById(apartment.getId()).isPresent(),
                "apartment not found");
        Assert.isTrue(!apartmentRepository.findById(291L).isPresent(),
                "non existing order found");
    }

    private Apartment createApartmentWithTestValues() {
        Apartment apartment = new Apartment();
        apartment.setFloorNumber("3");
        apartment.setBlockOfFlatsId("2");
        apartment.setAddress(new Address());

        return apartment;
    }

    private Address createAddressWithTestValues(){
        Address address = new Address();
        address.setStreet("testStreet");
        address.setPostalCode("12345");
        address.setNumber("1");
        address.setMunicipality("testMunicipality");
        address.setCity("testCity");
        address.setCountry("testCountry");

        return address;
    }
}
