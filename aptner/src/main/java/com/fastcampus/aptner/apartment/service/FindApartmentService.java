package com.fastcampus.aptner.apartment.service;

import com.fastcampus.aptner.apartment.domain.Apartment;

import java.util.Optional;

public interface FindApartmentService {
    Apartment findApartmentByName(String name);
    Apartment findApartmentById(Long apartmentId);
}
