package com.fastcampus.aptner.apartment.controller;

import com.fastcampus.aptner.apartment.dto.CreateApartmentRequest;
import com.fastcampus.aptner.apartment.service.CreateApartmentService;
import com.fastcampus.aptner.member.dto.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "아파트 추가하기", description = "아파트 추가")
@RequiredArgsConstructor
@RequestMapping("/api/apartment")
@RestController
public class CreateApartmentController {
    // TODO: 예외 및 유효성 검사 처리가 필요하다.

    private final CreateApartmentService apartmentService;

    @Operation(
            summary = "아파트 추가 API\n\n",
            description =
                    "name: 아파트 한글 이름(필수)\n\n" +
                    "engName: 아파트 영어 이름(선택)\n\n" +
                    "sido: 아파트 시(도)(필수)\n\n" +
                    "gugun: 아파트 구(군)(필수)\n\n" +
                    "road: 아파트 도로명 주소(필수)\n\n" +
                    "zipcode: 아파트 우편번호(필수)\n\n" +
                    "icon: 아파트 아이콘(선택)\n\n" +
                    "banner: 아파트 배너(선택)\n\n" +
                    "tel: 아파트 관리사무소 전화번호(선택)\n\n" +
                    "dutyTime: 아파트 관리사무소 영업시간(선택)"
    )
    @PostMapping("/create")
    public ResponseEntity<?> createApartment(@RequestBody CreateApartmentRequest request) {
        apartmentService.createApartment(request);
        return new ResponseEntity<>(new HttpResponse<>(1, "아파트 등록 성공", null), HttpStatus.CREATED);
    }
}
