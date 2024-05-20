package com.fastcampus.aptner.post.announcement.service.admin;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.announcement.repository.AnnouncementCategoryRepository;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import com.fastcampus.aptner.post.temp.service.ApartmentCommonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

import static com.fastcampus.aptner.post.common.error.PostErrorCode.CANT_DELETE;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnnouncementCategoryAdminServiceImpl implements AnnouncementCategoryAdminService{

    private final AnnouncementCategoryRepository announcementCategoryRepository;

    private final ApartmentCommonService apartmentService;

    @Override
    public ResponseEntity<HttpStatus> createAnnouncementCategory(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, AnnouncementDTO.AnnouncementCategoryReqDTO dto) {
        isCorrectApartment(userToken,apartmentId);
        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        announcementCategoryRepository.save(AnnouncementCategory.from(apartment,dto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateAnnouncementCategory(MemberTempDTO.MemberAuthDTO userToken, Long announcementCategoryId, AnnouncementDTO.AnnouncementCategoryReqDTO dto) {
        AnnouncementCategory announcementCategory = announcementCategoryRepository.findById(announcementCategoryId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncementCategory(userToken,announcementCategory);
        announcementCategory.updateCategory(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteAnnouncementCategory(MemberTempDTO.MemberAuthDTO userToken, Long announcementCategoryId) {
        AnnouncementCategory announcementCategory = announcementCategoryRepository.findById(announcementCategoryId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncementCategory(userToken,announcementCategory);
        if (!announcementCategory.getAnnouncementList().isEmpty()){
            throw new RestAPIException(CANT_DELETE);
        }
        announcementCategoryRepository.delete(announcementCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private static void isCorrectApartment(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId){
        if (userToken.ApartmentId()!= apartmentId){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

    private void checkApartmentByAnnouncementCategory(MemberTempDTO.MemberAuthDTO userToken, AnnouncementCategory announcementCategory){
        if (!Objects.equals(userToken.ApartmentId(), announcementCategory.getApartmentId().getApartmentId())){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }
}
