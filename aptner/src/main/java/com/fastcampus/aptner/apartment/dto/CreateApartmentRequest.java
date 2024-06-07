package com.fastcampus.aptner.apartment.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "아파트 생성 요청 DTO", description = "새로운 아파트 생성하기")
@Getter
public class CreateApartmentRequest {

    @Schema(description = "name: 아파트 한글 이름(필수)")
    private final String name;

    @Schema(description = "engName: 아파트 영어 이름(선택)")
    private final String engName;

    @Schema(description = "sido: 아파트 시(도)(필수)")
    private final String sido;

    @Schema(description = "gugun: 아파트 구(군)(필수)")
    private final String gugun;

    @Schema(description = "road: 아파트 도로명 주소(필수)")
    private final String road;

    @Schema(description = "zipcode: 아파트 우편번호(필수)")
    private final String zipcode;

    @Schema(description = "icon: 아파트 아이콘(선택)")
    private final String icon;

    @Schema(description = "banner: 아파트 배너(선택)")
    private final String banner;

    @Schema(description = "tel: 아파트 관리사무소 전화번호(선택)")
    private final String tel;

    @Schema(description = "dutyTime: 아파트 관리사무소 영업시간(선택)")
    private final String dutyTime;

    @Builder
    public CreateApartmentRequest(String name,String engName ,String sido, String gugun, String road, String zipcode, String icon, String banner, String tel, String dutyTime) {
        this.name = name;
        this.engName =engName;
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
