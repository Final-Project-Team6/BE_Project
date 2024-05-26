package com.fastcampus.aptner.post.communication.service;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.repository.CommunicationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fastcampus.aptner.post.common.error.PostErrorCode.NO_SUCH_POST;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommunicationCommonServiceImpl implements CommunicationCommonService {

    CommunicationRepository communicationRepository;

    @Override
    @Transactional
    public Communication getCommunicationEntity(Long communicationId) {
        return communicationRepository.findById(communicationId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
    }
}
