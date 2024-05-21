package com.fastcampus.aptner.apartment.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentCommonServiceImpl implements ApartmentCommonService {

    private final ApartmentRepository apartmentRepository;

    @Override
    public Apartment getApartmentById(Long id) {
        return apartmentRepository.findById(id).orElse(null);
    }
}
