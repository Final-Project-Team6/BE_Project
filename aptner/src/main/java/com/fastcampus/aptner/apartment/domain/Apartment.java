package com.fastcampus.aptner.apartment.domain;

import jakarta.persistence.*;
import lombok.*;

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
    @Builder
    public Apartment(String name, String sido, String gugun, String road, String zipcode, String icon, String banner, String tel, String dutyTime) {
        this.name = name;
        this.sido = sido;
        this.gugun = gugun;
        this.road = road;
        this.zipcode = zipcode;
        this.icon = icon;
        this.banner = banner;
        this.tel = tel;
        this.dutyTime = dutyTime;
    }
}
