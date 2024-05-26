package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.post.information.dto.InformationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InformationCategoryService {
    ResponseEntity<List<InformationDTO.InformationCategoryRespDTO>> getInformationCategoryList(Long apartmentId);
}
