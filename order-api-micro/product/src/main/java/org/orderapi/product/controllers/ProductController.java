package org.orderapi.product.controllers;

import org.orderapi.product.mappers.ProductMapper;
import org.orderapi.product.model.Product;
import org.orderapi.product.repositories.ProductRepository;
import org.orderapi.product.controllers.dto.ProductDto;
import org.orderapi.common.exception.ApplicationBadInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final String BLOCK_BY_ID_NOT_FOUND = "the block with the specific id does not exist";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping(path = "/{blockId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto fetchBlock(@PathVariable Long blockId) {
        Product product =
                productRepository.findById(blockId).orElseThrow(
                        () -> new ApplicationBadInputException(BLOCK_BY_ID_NOT_FOUND));
        return productMapper.toDto(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateBlock(@RequestBody ProductDto productDto) {
        return productMapper.toDto(productRepository.saveAndFlush(productMapper.toDomain(productDto)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto submitBlock(@RequestBody ProductDto productDto) {

        return productMapper.toDto(productRepository.saveAndFlush(productMapper.toDomain(productDto)));
    }
}
