package com.fastcampus.aptner.apartment.service;


import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.global.handler.exception.CustomDataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateApartmentServiceImpl implements UpdateApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Override
    public void updateApartmentName(Long apartmentId, String name) {
        apartmentRepository.findApartmentByApartmentId(apartmentId)
                .orElseThrow(() -> new CustomAPIException("아파트가 존재하지 않습니다."));

        int updatedRows = apartmentRepository.updateApartmentNameByApartmentId(apartmentId, name);

        if (updatedRows == 0) {
            throw new CustomDataNotFoundException("아파트가 존재하지 않습니다. [apartmentId]: " + apartmentId);
        }

    }
}
