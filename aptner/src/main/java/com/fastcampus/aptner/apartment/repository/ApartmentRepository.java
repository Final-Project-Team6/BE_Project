package com.fastcampus.aptner.apartment.repository;

import com.fastcampus.aptner.apartment.domain.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment,Long> {
    Optional<Apartment> findApartmentByName(String name);
    Optional<Apartment> findApartmentById(Long id);
}
