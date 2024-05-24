package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.service.ApartmentCommonService;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.announcement.repository.AnnouncementCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnnouncementCategoryServiceImpl implements AnnouncementCategoryService {

    private final AnnouncementCategoryRepository announcementCategoryRepository;

    private final ApartmentCommonService apartmentService;

    @Override
    public ResponseEntity<List<AnnouncementDTO.AnnouncementCategoryRespDTO>> getAnnouncementCategoryList(Long apartmentId) {
        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        List<AnnouncementCategory> list = announcementCategoryRepository.findAllByApartmentId(apartment);
        List<AnnouncementDTO.AnnouncementCategoryRespDTO> resp = list.stream().map(AnnouncementDTO.AnnouncementCategoryRespDTO::new).toList();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
