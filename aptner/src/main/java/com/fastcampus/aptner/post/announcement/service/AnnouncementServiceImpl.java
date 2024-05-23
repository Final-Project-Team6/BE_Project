package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.announcement.repository.AnnouncementRepository;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.service.CommentCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Slf4j
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    private final CommentCommonService commentCommonService;

    @Override
    public ResponseEntity<PageResponseDTO> searchAnnouncement(AnnouncementDTO.AnnouncementSearchReqDTO reqDTO) {

        PageRequest pageable = PageRequest.of(reqDTO.getPageNumber() - 1, reqDTO.getPageSize());
        reqDTO.setPageable(pageable);
        Page<Announcement> result = announcementRepository.searchAnnouncement(reqDTO);
        if (result.getContent().isEmpty()) {
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
    public ResponseEntity<AnnouncementDTO.AnnouncementRespDTO> getAnnouncement(Long announcementId, JWTMemberInfoDTO request) {
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(NoSuchElementException::new);
        AnnouncementDTO.AnnouncementRespDTO resp = new AnnouncementDTO.AnnouncementRespDTO(announcement, request);
        List<CommentDTO.ViewComments> comments = commentCommonService.getComments(announcementId, CommentType.ANNOUNCEMENT, request);
        resp.setComments(comments);
        announcement.addViewCount();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
