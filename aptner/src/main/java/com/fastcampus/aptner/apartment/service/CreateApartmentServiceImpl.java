package com.fastcampus.aptner.apartment.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.dto.CreateApartmentRequest;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateApartmentServiceImpl implements CreateApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Override
    @Transactional
    public void createApartment(CreateApartmentRequest request) {
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
