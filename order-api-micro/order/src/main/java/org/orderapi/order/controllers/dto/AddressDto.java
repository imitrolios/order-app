package org.orderapi.order.controllers.dto;


import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private String number;
    private String country;
    private String municipality;
    private String city;
    private String postalCode;
}
