package org.orderapi.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.orderapi.product.controllers.dto.AddressDto;
import org.orderapi.product.controllers.dto.ProductDto;
import org.orderapi.product.model.Address;
import org.orderapi.product.model.Product;
import org.orderapi.product.repositories.ProductRepository;
import org.orderapi.common.web.model.ErrorResponseDto;
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
class ProductApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSubmitProduct() throws Exception {

        ProductDto productDto = createProductRequestForTest();

        ObjectMapper om = new ObjectMapper();
        String orderRequestAsString = om.writeValueAsString(productDto);
        MvcResult productResult = this.mockMvc.perform(post("http://localhost:8081/product").
                content(orderRequestAsString)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        productDto = om.readValue(productResult.getResponse().getContentAsString(), ProductDto.class);

        assertEquals(productResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE,
                "Content type not JSON");
        assertEquals(productResult.getResponse().getStatus(), HttpStatus.CREATED.value(),
                "HttpStatus not 201 Created");
        assertNotNull(productDto.getId());
        assertEquals(productDto.getAddressDto().getPostalCode(), "15771");
    }

    @Test
    void testFetchProductById() throws Exception {
        Product product = createProductForTest();
        productRepository.saveAndFlush(product);

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8081/product/" + product.getId())
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        ProductDto productDto = om.readValue(mvcResult.getResponse().getContentAsString(), ProductDto.class);

        assertEquals(mvcResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        assertNotNull(productDto);
        assertNotNull(productDto.getId());
        assertNotNull(productDto.getAddressDto());
    }

    @Test
    void testFetchProductByIdThatDoesNotExistReturns400() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8081/product/" + (long) (100 * Math.random()))
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

    private Product createProductForTest() {
        Product product = new Product();
        product.setAddress(createAddress());

        return product;
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


    private ProductDto createProductRequestForTest() {

        ProductDto productDto = new ProductDto();
        productDto.setAddressDto(createAddressRequestForTest());

        return productDto;
    }

    private AddressDto createAddressRequestForTest() {
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
        productRepository.deleteAll();
    }

}
