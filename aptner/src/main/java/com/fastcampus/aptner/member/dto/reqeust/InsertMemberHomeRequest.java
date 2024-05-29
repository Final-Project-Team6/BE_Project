package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(name = "회원 자택 추가하기 DTO", description = "회원 자택 추가하기")
@RequiredArgsConstructor
@Getter
public class InsertMemberHomeRequest {

    @Schema(description = "회원 활성화 수정 요청 Body -> dong: 아파트 동")
    private Long apartmentId;

    @Schema(description = "회원 활성화 수정 요청 Body -> dong: 아파트 동(필수)")
    @NotBlank(message = "아파트 동(층): 필수 정보입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,8}$"
            , message = "동(층): 영어 대소문자, 한글, 숫자만 입력 가능하며, 1자 이상 8자 이하여야 합니다.")
    @Size(min = 1, max = 8)
    private String dong;

    @Schema(description = "회원 활성화 수정 요청 Body -> ho: 아파트 호(필수)")
    @NotBlank(message = "아파트 호: 필수 정보입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,8}$"
            , message = "아파트 호: 영어 대소문자, 한글, 숫자만 입력 가능하며, 1자 이상 8자 이하여야 합니다.")
    @Size(min = 1, max = 8)
    private String ho;

}
