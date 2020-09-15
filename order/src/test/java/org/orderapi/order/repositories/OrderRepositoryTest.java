package org.orderapi.order.repositories;

import org.junit.jupiter.api.Test;
import org.orderapi.order.model.Address;
import org.orderapi.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateOrder() {
        Order order = createOrderWithTestValues();
        order.setAddress(createAddressWithTestValues());
        orderRepository.save(order);

        Assert.isTrue(orderRepository.findById(order.getId()).isPresent(),
                "order not found");
        Assert.isTrue(orderRepository.findById(291L).isEmpty(),
                "non existing order found");
    }

    private Order createOrderWithTestValues() {
        Order order = new Order();
        order.setAddress(new Address());

        return order;
    }

    private Address createAddressWithTestValues() {
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
