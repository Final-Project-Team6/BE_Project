package com.fastcampus.aptner.apartment.controller;

import com.fastcampus.aptner.apartment.service.UpdateApartmentService;
import com.fastcampus.aptner.member.dto.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "아파트 수정하기", description = "아파트 수정하기")
@RequiredArgsConstructor
@RequestMapping("/api/apartment")
@RestController
public class UpdateApartmentController {

    private final UpdateApartmentService apartmentService;

    @Operation(
            summary = "아파트 이름 수정 API\n\n",
            description = "apartmentId: 아파트 고유 식별 번호\n\n" +
                    "newName: 아파트 새로운 이름"
    )
    @PostMapping("/update/{apartmentId}")
    public ResponseEntity<?> updateApartmentName(@PathVariable Long apartmentId, @RequestBody String newName) {
        apartmentService.updateApartmentName(apartmentId, newName);
        return new ResponseEntity<>(new HttpResponse<>(1, "아파트 이름 업데이트 성공", null), HttpStatus.OK);
    }
}
