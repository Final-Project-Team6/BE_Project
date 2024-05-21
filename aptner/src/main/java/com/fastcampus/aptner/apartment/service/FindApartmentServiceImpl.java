package com.fastcampus.aptner.apartment.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.dto.FindApartmentResponse;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FindApartmentServiceImpl implements FindApartmentService {

    private final ApartmentRepository apartmentRepository;

    // TODO: 예외 처리 수정하기
    public FindApartmentResponse findApartmentByName(String name) {
        Apartment apartment = apartmentRepository.findApartmentByName(name)
                .orElseThrow(() -> new CustomAPIException("아파트가 존재하지 않습니다."));

        FindApartmentResponse response = FindApartmentResponse.builder()
                .apartmentId(apartment.getApartmentId())
                .name(apartment.getName())
                .sido(apartment.getSido())
                .gugun(apartment.getGugun())
                .road(apartment.getRoad())
                .zipcode(apartment.getZipcode())
                .icon(apartment.getIcon())
                .banner(apartment.getBanner())
                .tel(apartment.getTel())
                .dutyTime(apartment.getDutyTime())
                .build();

        return response;
    }

    @Override
    public Apartment findApartmentById(Long apartmentId) {
        return apartmentRepository.findApartmentByApartmentId(apartmentId)
                .orElseThrow(() -> new CustomAPIException("아파트가 존재하지 않습니다."));
    }
}
