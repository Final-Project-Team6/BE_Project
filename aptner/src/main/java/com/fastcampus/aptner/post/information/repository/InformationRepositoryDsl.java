package com.fastcampus.aptner.post.information.repository;

import com.fastcampus.aptner.post.information.domain.Information;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import org.springframework.data.domain.Page;

public interface InformationRepositoryDsl {
    Page<Information> searchInformation(InformationDTO.InformationSearchReqDTO reqDTO);
}
