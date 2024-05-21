package com.fastcampus.aptner.apartment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "아파트 이름으로 아파트 조회 응답",description = "아파트 이름으로 검색된 아파트의 모든 데이터")
@Getter
public class FindApartmentResponse {

    @Schema(description = "apartmentId: 아파트 고유 식별 번호")
    private Long apartmentId;

    @Schema(description = "name: 아파트 이름")
    private String name;

    @Schema(description = "sido: 아파트 시(도)")
    private String sido;

    @Schema(description = "gugun: 아파트 구(군)")
    private String gugun;


    @Schema(description = "road: 아파트 도로명")
    private String road;

    @Schema(description = "zipcode: 아파트 우편번호")
    private String zipcode;

    @Schema(description = "icon: 아파트 아이콘")
    private String icon;

    @Schema(description = "banner: 아파트 배너")
    private String banner;

    @Schema(description = "tel: 아파트 번호")
    private String tel;

    @Schema(description = "dutyTime: 아파트 시간")
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
