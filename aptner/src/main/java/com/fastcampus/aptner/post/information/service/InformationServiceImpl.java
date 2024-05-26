package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.information.domain.Information;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.repository.InformationRepository;
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

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InformationServiceImpl implements InformationService {

    InformationRepository informationRepository;

    @Override
    @Transactional
    public ResponseEntity<PageResponseDTO> searchInformation(InformationDTO.InformationSearchReqDTO reqDTO) {
        PageRequest pageable = PageRequest.of(reqDTO.getPageNumber()-1,reqDTO.getPageSize());
        reqDTO.setPageable(pageable);
        Page<Information> result = informationRepository.searchInformation(reqDTO);
        if (result.getContent().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PageResponseDTO resp = new PageResponseDTO(result);
        resp.setContent(
                result
                        .getContent()
                        .stream()
                        .map(InformationDTO.InformationListRespDTO::new)
                        .toList()
        );
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<InformationDTO.InformationRespDTO> getInformation(Long informationId, JWTMemberInfoDTO token) {
        Information information = informationRepository.findById(informationId).orElseThrow(NoSuchElementException::new);
        InformationDTO.InformationRespDTO resp = new InformationDTO.InformationRespDTO(information,token);
        information.addViewCount();
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

}
