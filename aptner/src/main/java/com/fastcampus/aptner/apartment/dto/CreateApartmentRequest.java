package com.fastcampus.aptner.apartment.dto;


import lombok.Builder;
import lombok.Getter;


@Getter
public class CreateApartmentRequest {

    private final String name;
    private final String sido;
    private final String gugun;
    private final String road;
    private final String zipcode;
    private final String icon;
    private final String banner;
    private final String tel;
    private final String dutyTime;

    @Builder
    public CreateApartmentRequest(String name, String sido, String gugun, String road, String zipcode, String icon, String banner, String tel, String dutyTime) {
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
