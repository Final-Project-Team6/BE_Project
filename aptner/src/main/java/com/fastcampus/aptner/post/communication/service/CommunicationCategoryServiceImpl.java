package com.fastcampus.aptner.post.communication.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.post.communication.domain.CommunicationCategory;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.communication.repository.CommunicationCategoryRepository;
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
public class CommunicationCategoryServiceImpl implements CommunicationCategoryService {

    CommunicationCategoryRepository communicationCategoryRepository;
    ApartmentRepository apartmentRepository;

    @Override
    @Transactional
    public ResponseEntity<List<CommunicationDTO.CommunicationCategoryRespDTO>> getCommunicationCategoryList(Long apartmentId) {
        Apartment apartment = apartmentRepository.findApartmentByApartmentId(apartmentId).orElseThrow(() -> new CustomAPIException("아파트가 존재하지 않습니다."));
        List<CommunicationCategory> list = communicationCategoryRepository.findAllByApartmentId(apartment);
        List<CommunicationDTO.CommunicationCategoryRespDTO> resp = list.stream().map(CommunicationDTO.CommunicationCategoryRespDTO::new).toList();
        if(list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
