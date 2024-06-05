package com.fastcampus.aptner.post.communication.service;


import com.fastcampus.aptner.post.communication.domain.CommunicationType;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommunicationCategoryService {
    ResponseEntity<List<CommunicationDTO.CommunicationCategoryRespDTO>> getCommunicationCategoryList(Long apartmentId, CommunicationType communicationType);
}
