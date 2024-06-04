package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.service.ApartmentCommonService;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
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
    public ResponseEntity<List<AnnouncementDTO.AnnouncementCategoryRespDTO>> getAnnouncementCategoryList(Long apartmentId, AnnouncementType announcementType) {
        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        List<AnnouncementCategory> list;
        if (announcementType== AnnouncementType.NOTICE || announcementType == AnnouncementType.DISCLOSURE){
            list = announcementCategoryRepository.findAllByApartmentIdAndType(apartment,announcementType);
        }else if (announcementType == null){
            list = announcementCategoryRepository.findAllByApartmentId(apartment);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<AnnouncementDTO.AnnouncementCategoryRespDTO> resp = list.stream().map(AnnouncementDTO.AnnouncementCategoryRespDTO::new).toList();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
