package com.fastcampus.aptner.apartment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "아파트 이름 요청",description = "아파트의 모든 정보를 검색할 아파트 이름 작성")
@Getter
@Setter
public class FindApartmentRequest {

    @Schema(description = "apartmentName: 아파트 이름")
    private String apartmentName;
}
