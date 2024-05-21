package com.fastcampus.aptner.apartment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindApartmentResponse {

    private Long apartmentId;
    private String name;
    private String sido;
    private String gugun;
    private String road;
    private String zipcode;
    private String icon;
    private String banner;
    private String tel;
    private String dutyTime;

    @Builder
    public FindApartmentResponse(Long apartmentId, String name, String sido, String gugun, String road, String zipcode, String icon, String banner, String tel, String dutyTime) {
        this.apartmentId = apartmentId;
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
