package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.announcement.repository.AnnouncementRepository;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.service.CommentCommonService;
import com.fastcampus.aptner.post.opinion.service.CommentService;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.fastcampus.aptner.global.error.CommonErrorCode.INVALID_PARAMETER;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    private final CommentCommonService commentCommonService;

    @Override
    public ResponseEntity<PageResponseDTO> searchAnnouncement(AnnouncementDTO.AnnouncementSearchReqDTO reqDTO) {

        PageRequest pageable = PageRequest.of(reqDTO.getPageNumber()-1, reqDTO.getPageSize());
        reqDTO.setPageable(pageable);
        Page<Announcement> result = announcementRepository.searchAnnouncement(reqDTO);
        if (result.getContent().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PageResponseDTO resp = new PageResponseDTO(result);
        resp.setContent(
                result
                        .getContent()
                        .stream()
                        .map(AnnouncementDTO.AnnouncementListRespDTO::new)
                        .toList()
        );
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }



    @Override
    @Transactional
    public ResponseEntity<AnnouncementDTO.AnnouncementRespDTO> getAnnouncement(Long announcementId, MemberTempDTO.MemberAuthDTO token) {
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(NoSuchElementException::new);
        AnnouncementDTO.AnnouncementRespDTO resp =new AnnouncementDTO.AnnouncementRespDTO(announcement,token);
        List<CommentDTO.ViewComments> comments = commentCommonService.getComments(announcementId, CommentType.ANNOUNCEMENT,token);
        resp.setComments(comments);
        announcement.addViewCount();
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }
}
