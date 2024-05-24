package com.fastcampus.aptner.apartment.controller;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.dto.FindApartmentResponse;
import com.fastcampus.aptner.apartment.service.FindApartmentService;
import com.fastcampus.aptner.member.dto.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "아파트 찾기", description = "아파트 이름으로 단건 조회, 아파트 단순 전체 조회, 아파트 이름으로 검색, 아파트 ID 로 단건 조회")
@RequiredArgsConstructor
@RequestMapping("/api/apartment")
@RestController
public class FindApartmentController {
    // TODO: 예외 및 유효성 검사 처리가 필요하다.

    private final FindApartmentService apartmentService;

    @Operation(
            summary = "아파트 이름으로 아파트 단건 조회 API\n\n",
            description = "Schema -> apartmentName: 아파트 이름"
    )
    @GetMapping("/find")
    public ResponseEntity<?> findByApartmentName(@RequestParam String apartmentName) {
        FindApartmentResponse response = apartmentService.findApartmentByName(apartmentName);
        return new ResponseEntity<>(new HttpResponse<>(1, "아파트 조회 성공", response), HttpStatus.OK);
    }

    @Operation(
            summary = "아파트 단순 전체 조회 API\n\n"
    )
    @GetMapping("/")
    public ResponseEntity<?> findAllApartment(){
        return apartmentService.findAllApartment();
    }
    @Operation(
            summary = "아파트 이름으로 검색 API\n\n"
    )
    @GetMapping("/search")
    public ResponseEntity<?> searchApartment(@RequestParam String keyword){
        return apartmentService.searchApartment(keyword);
    }
    @Operation(
            summary = "아파트 ID로 단건 조회 API\n\n"
    )
    @GetMapping("/{apartmentId}")
    public ResponseEntity<?> findApartmentById(@PathVariable Long apartmentId){
        Apartment apartment = apartmentService.findApartmentById(apartmentId);
        return new ResponseEntity<>(new FindApartmentResponse(apartment),HttpStatus.OK);
    }

}
