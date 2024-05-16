package com.fastcampus.aptner.post.complaint.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComplaintType {

    MANAGEMENT_OFFICE("관리사무소"), RESIDENTS_COMMITTEE("입주자 대표회의");

    private final String ComplaintType;

}
