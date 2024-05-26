package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.post.information.domain.InformationCategory;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.repository.InformationCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InformationCategoryServiceImpl implements InformationCategoryService {

    InformationCategoryRepository informationCategoryRepository;
    ApartmentRepository apartmentRepository;

    @Override
    @Transactional
    public ResponseEntity<List<InformationDTO.InformationCategoryRespDTO>> getInformationCategoryList(Long apartmentId) {
        Apartment apartment = apartmentRepository.findApartmentByApartmentId(apartmentId).get();
        List<InformationCategory> list = informationCategoryRepository.findAllByApartmentId(apartment);
        List<InformationDTO.InformationCategoryRespDTO> resp = list.stream().map(InformationDTO.InformationCategoryRespDTO::new).toList();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
