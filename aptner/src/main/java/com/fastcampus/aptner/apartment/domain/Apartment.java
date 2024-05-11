package com.fastcampus.aptner.apartment.domain;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "apartment")
@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_id")
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String sido;

    @Column(length = 100)
    private String gugun;

    @Column(length = 100)
    private String road;

    @Column(length = 50)
    private String zipcode;

    @Column
    private String icon;

    @Column
    private String banner;

    @Column
    private String tel;

    @Column
    private String dutyTime;

}
