package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.post.information.domain.InformationCategory;
import com.fastcampus.aptner.post.information.domain.InformationType;
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

import java.util.EnumSet;
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
    public ResponseEntity<List<InformationDTO.InformationCategoryRespDTO>> getInformationCategoryList(Long apartmentId, InformationType informationType) {
        Apartment apartment = apartmentRepository.findApartmentByApartmentId(apartmentId).orElseThrow(() -> new CustomAPIException("아파트가 존재하지 않습니다."));
        List<InformationCategory> list;
        if (informationType == null) {
            list = informationCategoryRepository.findAllByApartmentId(apartment);
        } else if (EnumSet.of(InformationType.THANKS, InformationType.COMPLEX_VIEW,
                InformationType.TEL_INFO, InformationType.FACILITY, InformationType.ETC).contains(informationType)) {
            list = informationCategoryRepository.findAllByApartmentIdAndType(apartment, informationType);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<InformationDTO.InformationCategoryRespDTO> resp = list.stream().map(InformationDTO.InformationCategoryRespDTO::new).toList();
        if(list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
