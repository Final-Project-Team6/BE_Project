package com.fastcampus.aptner.apartment.controller;

import com.fastcampus.aptner.apartment.dto.CreateApartmentRequest;
import com.fastcampus.aptner.apartment.service.CreateApartmentService;
import com.fastcampus.aptner.member.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/apartment")
@RestController
public class CreateApartmentController {
    // TODO: 예외 및 유효성 검사 처리가 필요하다.

    private final CreateApartmentService apartmentService;

    @PostMapping("/create")
    public ResponseEntity<?> createApartment(@RequestBody CreateApartmentRequest request) {
        apartmentService.createApartment(request);
        return new ResponseEntity<>(new HttpResponse<>(1, "아파트 등록 성공", null), HttpStatus.CREATED);
    }
}
