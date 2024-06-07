package com.fastcampus.aptner.apartment.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.dto.CreateApartmentRequest;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.global.handler.exception.CustomValidationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CreateApartmentServiceImpl implements CreateApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Override
    @Transactional
    public void createApartment(CreateApartmentRequest request) {
        Optional<Apartment> apartmentByName = apartmentRepository.findApartmentByName(request.getName());
        if (apartmentByName.isPresent()) {
            if (apartmentByName.get().getZipcode().equals(request.getZipcode())) {
                throw new CustomAPIException("이미 등록된 아파트입니다.");
            }
        }

        Apartment apartment = Apartment.builder()
                .name(request.getName())
                .engName(request.getEngName())
                .sido(request.getSido())
                .gugun(request.getGugun())
                .road(request.getRoad())
                .zipcode(request.getZipcode())
                .icon(request.getIcon())
                .banner(request.getBanner())
                .tel(request.getTel())
                .dutyTime(request.getDutyTime())
                .build();

        apartmentRepository.save(apartment);
    }
}
