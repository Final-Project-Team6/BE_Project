package com.fastcampus.aptner.post.announcement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnnouncementType {
    NOTICE("공지사항"), DISCLOSURE("의무공개 사항");
    private final String AnnouncementType;
}
