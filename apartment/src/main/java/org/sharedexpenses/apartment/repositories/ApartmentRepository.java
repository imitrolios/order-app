package org.sharedexpenses.apartment.repositories;

import org.sharedexpenses.apartment.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
