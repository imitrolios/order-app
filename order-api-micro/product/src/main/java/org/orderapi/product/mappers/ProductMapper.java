package org.orderapi.product.mappers;

import org.orderapi.product.controllers.dto.ProductDto;
import org.orderapi.product.model.Product;

public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toDomain(ProductDto productDto);

}
