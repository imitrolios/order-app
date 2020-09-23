package org.orderapi.product.mappers;

import org.junit.jupiter.api.Test;
import org.orderapi.product.controllers.dto.AddressDto;
import org.orderapi.product.controllers.dto.ProductDto;
import org.orderapi.product.model.Address;
import org.orderapi.product.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductMapperTest {
    private final ProductMapper productMapper = new ProductMapperImpl(new AddressMapperImpl());

    @Test
    public void when_productDomain_mapper_should_return_productDto() {
        Product product = new Product();
        product.setId(1L);
        product.setAddress(new Address());

        ProductDto productDto = productMapper.toDto(product);

        assertNotNull(productDto, "productDto is null");
        assertEquals(productDto.getId(), product.getId(), "productDto.id not equal to product.id");
        assertNotNull(productDto.getAddressDto(), "productDto.addressDto is null");
    }

    @Test
    public void when_productDto_mapper_should_return_productDomain() {

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setAddressDto(new AddressDto());

        Product product = productMapper.toDomain(productDto);

        assertNotNull(product, "product is null");
        assertEquals(product.getId(), productDto.getId(), "productDto.id not equal to product.id");
        assertNotNull(product.getAddress(), "productDto.addressDto is null");
    }

}