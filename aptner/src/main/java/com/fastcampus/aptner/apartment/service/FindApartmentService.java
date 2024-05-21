package com.fastcampus.aptner.apartment.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.dto.FindApartmentResponse;

import java.util.Optional;

public interface FindApartmentService {
    FindApartmentResponse findApartmentByName(String name);
    Apartment findApartmentById(Long apartmentId);
}
