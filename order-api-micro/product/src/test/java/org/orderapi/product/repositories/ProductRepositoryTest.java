package org.orderapi.product.repositories;

import org.junit.jupiter.api.Test;
import org.orderapi.product.model.Address;
import org.orderapi.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateBlock() {
        Product product = createBlockWithTestValues();
        product.setAddress(createAddressWithTestValues());
        productRepository.save(product);

        Assert.isTrue(productRepository.findById(product.getId()).isPresent(),
                "block not found");
        Assert.isTrue(productRepository.findById(291L).isEmpty(),
                "non existing block found");
    }

    private Product createBlockWithTestValues() {
        Product product = new Product();
        product.setAddress(new Address());

        return product;
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
