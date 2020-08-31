package org.sharedexpenses.apartment.controllers.dto;

import lombok.Data;

@Data
public class ApartmentDto {
    private Long id;
    private AddressDto addressDto;
    private String floorNumber;
    private String blockOfFlatsId;
}
