package com.fastcampus.aptner.apartment.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FindApartmentServiceImpl implements FindApartmentService {

    private final ApartmentRepository apartmentRepository;

    // TODO: 예외 처리 수정하기
    public Apartment findApartmentByName(String name) {
        return apartmentRepository.findApartmentByName(name)
                .orElseThrow(() -> new IllegalArgumentException("아파트가 존재하지 않습니다."));
    }

    @Override
    public Apartment findApartmentById(Long apartmentId) {
        return apartmentRepository.findApartmentByApartmentId(apartmentId)
                .orElseThrow(() -> new IllegalArgumentException("아파트가 존재하지 않습니다."));
    }
}
