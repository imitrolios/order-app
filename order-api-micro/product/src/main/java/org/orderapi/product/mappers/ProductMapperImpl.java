package org.orderapi.product.mappers;

import org.orderapi.product.model.Product;
import org.orderapi.product.controllers.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private AddressMapper addressMapper;

    public ProductMapperImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setAddressDto(addressMapper.toDto(product.getAddress()));
        return productDto;
    }

    @Override
    public Product toDomain(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setAddress(addressMapper.toDomain(productDto.getAddressDto()));
        return product;
    }
}
