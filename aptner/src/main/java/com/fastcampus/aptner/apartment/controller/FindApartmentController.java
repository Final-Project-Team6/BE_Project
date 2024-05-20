package com.fastcampus.aptner.apartment.controller;

import com.fastcampus.aptner.apartment.dto.FindApartmentResponse;
import com.fastcampus.aptner.apartment.service.FindApartmentService;
import com.fastcampus.aptner.member.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/apartment")
@RestController
public class FindApartmentController {
    // TODO: 예외 및 유효성 검사 처리가 필요하다.

    private final FindApartmentService apartmentService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search/{apartmentName}")
    public ResponseEntity<?> findByApartmentName(@PathVariable(name = "apartmentName") String apartmentName) {
        FindApartmentResponse response = apartmentService.findApartmentByName(apartmentName);
        return new ResponseEntity<>(new HttpResponse<>(1, "아파트 조회 성공", response), HttpStatus.OK);
    }
}
