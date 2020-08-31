package org.sharedexpenses.apartment.controllers;

import org.sharedexpenses.apartment.controllers.dto.ApartmentDto;
import org.sharedexpenses.apartment.mappers.ApartmentMapper;
import org.sharedexpenses.apartment.model.Apartment;
import org.sharedexpenses.apartment.repositories.ApartmentRepository;
import org.sharedexpenses.common.exception.ApplicationBadInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apartment")
public class ApartmentController {

    private final String APARTMENT_BY_ID_NOT_FOUND = "the apartment with the specific id does not exist";

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ApartmentMapper apartmentMapper;

    @GetMapping(path = "/{apartmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDto fetchApartment(@PathVariable Long apartmentId) {
        Apartment apartment =
                apartmentRepository.findById(apartmentId).orElseThrow(
                        () -> new ApplicationBadInputException(APARTMENT_BY_ID_NOT_FOUND));
        return apartmentMapper.toDto(apartment);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDto updateApartment(@RequestBody ApartmentDto apartmentDto) {
        return apartmentMapper.toDto(apartmentRepository.saveAndFlush(apartmentMapper.toDomain(apartmentDto)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDto submitApartment(@RequestBody ApartmentDto apartmentDto) {

        return apartmentMapper.toDto(apartmentRepository.saveAndFlush(apartmentMapper.toDomain(apartmentDto)));
    }
}
