package com.fastcampus.aptner.apartment.controller;

import com.fastcampus.aptner.apartment.service.UpdateApartmentService;
import com.fastcampus.aptner.member.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/apartment")
@RestController
public class UpdateApartmentController {
    // TODO: 예외 및 유효성 검사 처리가 필요하다.

    private final UpdateApartmentService apartmentService;

    @PostMapping("/update-name/{apartmentId}")
    public ResponseEntity<?> updateApartmentName(@PathVariable Long apartmentId, @RequestBody String newName) {
        apartmentService.updateApartmentName(apartmentId, newName);
        return new ResponseEntity<>(new HttpResponse<>(1, "아파트 이름 업데이트 성공", null), HttpStatus.OK);
    }
}
