package com.fastcampus.aptner.apartment.controller;

import com.fastcampus.aptner.apartment.dto.CreateApartmentRequest;
import com.fastcampus.aptner.apartment.service.CreateApartmentService;
import com.fastcampus.aptner.member.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/apartment")
@RestController
public class CreateApartmentController {

    private final CreateApartmentService apartmentService;

    @PostMapping("/insert")
    public ResponseEntity<?> saveApartment(@RequestBody CreateApartmentRequest request) {
        apartmentService.createApartment(request);
        return new ResponseEntity<>(new HttpResponse<>(1, "아파트 등록 성공", null), HttpStatus.CREATED);
    }
}
