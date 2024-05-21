package com.fastcampus.aptner.apartment.controller;

import com.fastcampus.aptner.apartment.dto.FindApartmentRequest;
import com.fastcampus.aptner.apartment.dto.FindApartmentResponse;
import com.fastcampus.aptner.apartment.service.FindApartmentService;
import com.fastcampus.aptner.member.dto.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "아파트 이름으로 검색(모든 사용자)", description = "아파트 이름으로 아파트 조회")
@RequiredArgsConstructor
@RequestMapping("/api/apartment")
@RestController
public class FindApartmentController {
    // TODO: 예외 및 유효성 검사 처리가 필요하다.

    private final FindApartmentService apartmentService;

    @Operation(
            summary = "아파트 이름으로 아파트 조회 API",
            description = "apartmentName : 검색할 아파트 이름")
    @GetMapping("/search")
    public ResponseEntity<?> findByApartmentName(@RequestBody FindApartmentRequest request) {
        FindApartmentResponse response = apartmentService.findApartmentByName(request.getApartmentName());
        return new ResponseEntity<>(new HttpResponse<>(1, "아파트 조회 성공", response), HttpStatus.OK);
    }
}
