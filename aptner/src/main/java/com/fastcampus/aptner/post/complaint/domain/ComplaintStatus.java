package com.fastcampus.aptner.post.complaint.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComplaintStatus {
    SUBMITTED("제출됨"),
    VERIFIED("확인됨"),
    INVESTIGATING("조사중"),
    PROCESSING("처리중"),
    RESPONDED("답변됨"),
    COMPLETED("완료됨");

    private final String ComplaintStatus;

}