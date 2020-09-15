package org.orderapi.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.orderapi.common.web.model.ErrorResponseDto;
import org.orderapi.order.controllers.dto.AddressDto;
import org.orderapi.order.controllers.dto.OrderDto;
import org.orderapi.order.model.Address;
import org.orderapi.order.model.Order;
import org.orderapi.order.repositories.OrderRepository;
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
class OrderApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testSubmitOrder() throws Exception {

        OrderDto orderDto = createOrderRequestForTest();

        ObjectMapper om = new ObjectMapper();
        String orderRequestAsString = om.writeValueAsString(orderDto);
        MvcResult orderResult = this.mockMvc.perform(post("http://localhost:8081/order").
                content(orderRequestAsString)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        orderDto = om.readValue(orderResult.getResponse().getContentAsString(), OrderDto.class);

        assertEquals(orderResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE,
                "Content type not JSON");
        assertEquals(orderResult.getResponse().getStatus(), HttpStatus.CREATED.value(),
                "HttpStatus not 201 Created");
        assertNotNull(orderDto.getId(),"orderDto.id is null");
        assertEquals(orderDto.getAddressDto().getPostalCode(), "15771", "orderDto.addressDto.postalCode" +
                "ne to 15771 hardcoded initial value");
    }

    @Test
    void testFetchOrderById() throws Exception {
        Order order = createOrderForTest();
        orderRepository.saveAndFlush(order);

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8081/order/" + order.getId())
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        OrderDto orderDto = om.readValue(mvcResult.getResponse().getContentAsString(), OrderDto.class);

        assertEquals(mvcResult.getResponse().getContentType(), MediaType.APPLICATION_JSON_VALUE);
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        assertNotNull(orderDto);
        assertNotNull(orderDto.getId());
        assertNotNull(orderDto.getAddressDto());
    }

    @Test
    void testFetchOrderByIdThatDoesNotExistReturns400() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8081/order/" + (long) (100 * Math.random()))
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

    private Order createOrderForTest() {
        Order order = new Order();
        order.setAddress(createAddress());

        return order;
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


    private OrderDto createOrderRequestForTest() {

        OrderDto orderDto = new OrderDto();
        orderDto.setAddressDto(createAddressRequestForTest());

        return orderDto;
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
        orderRepository.deleteAll();
    }

}
