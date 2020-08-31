package org.sharedexpenses.apartment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.sharedexpenses.apartment.controllers.dto.AddressDto;
import org.sharedexpenses.apartment.controllers.dto.ApartmentDto;
import org.sharedexpenses.apartment.model.Address;
import org.sharedexpenses.apartment.model.Apartment;
import org.sharedexpenses.apartment.repositories.ApartmentRepository;
import org.sharedexpenses.common.web.model.ErrorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApartmentApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    void testSubmitApartment() throws Exception {

        ApartmentDto apartmentDto = createApartmentRequestForTest();

        ObjectMapper om = new ObjectMapper();
        String orderRequestAsString = om.writeValueAsString(apartmentDto);
        MvcResult apartmentResult = this.mockMvc.perform(post("http://localhost:8081/apartment").
                content(orderRequestAsString)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        apartmentDto = om.readValue(apartmentResult.getResponse().getContentAsString(), ApartmentDto.class);

        assertEquals(apartmentResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE,
                "Content type not JSON");
        assertEquals(apartmentResult.getResponse().getStatus(), HttpStatus.CREATED.value(),
                "HttpStatus not 201 Created");
        assertEquals(apartmentDto.getFloorNumber(), "2");
        assertNotNull(apartmentDto.getId());
        assertEquals(apartmentDto.getAddressDto().getPostalCode(), "15771");
    }

    @Test
    void testFetchApartmentById() throws Exception {
        Apartment apartment = createApartmentForTest();
        apartmentRepository.saveAndFlush(apartment);

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8081/apartment/" + apartment.getId())
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        ApartmentDto apartmentDto = om.readValue(mvcResult.getResponse().getContentAsString(), ApartmentDto.class);

        assertEquals(mvcResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        assertNotNull(apartmentDto);
        assertNotNull(apartmentDto.getId());
        assertNotNull(apartmentDto.getAddressDto());
        assertNotNull(apartmentDto.getAddressDto());
        assertEquals(apartmentDto.getFloorNumber(), apartment.getFloorNumber());
    }

    @Test
    void testFetchApartmentByIdThatDoesNotExistReturns400() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8081/apartment/" + (long) (100 * Math.random()))
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        ErrorResponseDto errorResponseDto = om.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDto.class);

        assertEquals(mvcResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        assertEquals(errorResponseDto.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(errorResponseDto.getError());
        assertNotNull(errorResponseDto.getMessage());
    }

    private Apartment createApartmentForTest() {
        Apartment apartment = new Apartment();
        apartment.setFloorNumber("2");
        apartment.setBlockOfFlatsId("3");
        apartment.setAddress(createAddress());

        return apartment;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setCity("testCity");
        address.setCountry("testCountry");
        address.setMunicipality("testMunicipality");
        address.setNumber("3");
        address.setPostalCode("testPostalCode");
        address.setStreet("testStreet");

        return address;
    }


    private ApartmentDto createApartmentRequestForTest() {

        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setFloorNumber("2");
        apartmentDto.setBlockOfFlatsId("3");
        apartmentDto.setAddressDto(createAddressRequestFortest());

        return apartmentDto;
    }

    private AddressDto createAddressRequestFortest() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("Athens");
        addressDto.setCountry("Greece");
        addressDto.setMunicipality("Zografou");
        addressDto.setNumber("37");
        addressDto.setPostalCode("15771");
        addressDto.setStreet("Xenias");
        return addressDto;
    }

    @AfterEach
    private void truncateTablesInDb() {
        apartmentRepository.deleteAll();
    }

}
