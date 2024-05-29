package com.fastcampus.aptner.member.dto.reqeust;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinMemberRequest {

    private final Boolean termsService; // 서비스 이용 약관 동의

    private final Boolean privateInformationCollection; // 개인 정보 수집 동의

    private final Boolean snsMarketingInformationReceive; // SNS 마케팅 정보 수신 동의는 선택입니다.

    @NotBlank(message = "이름: 필수 정보입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,16}$", message = "이름: 1자 이상 16자 이하의 이름을 입력해주세요.")
    @Size(min = 1, max = 16)
    private final String fullName;

    @Pattern(regexp = "^\\d{6}$", message = "주민등록번호: 6자리 숫자를 입력해주세요.")
    @Size(max = 6)
    private final String birthFirst; // 본인인증없이 가입된 회원은 선택입니다.

    @Pattern(regexp = "^[1-4]$", message = "주민등록번호: 1자리 숫자를 입력해주세요.")
    @Size(max = 1)
    private final String gender;  // 본인인증없이 가입된 회원은 선택입니다.


    @NotBlank(message = "휴대전화번호: 필수 정보입니다.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "휴대전화번호: 숫자만 입력해주세요.")
    @Size(max = 11)
    private final String phone;

    @NotBlank(message = "아이디: 필수 정보입니다.")
    @Pattern(regexp = "^[a-z0-9_-]{5,20}$", message = "아이디: 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    @Size(min = 5, max = 20)
    private final String username;

    @NotBlank(message = "비밀번호: 필수 정보입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}"
            , message = "비밀번호: 8~20자 영문 대소문자, 숫자, 특수문자를 조합하여 작성해야 합니다.")
    @Size(min = 8, max = 20)
    private final String password;

    @NotBlank(message = "닉네임: 필수 정보입니다.")
    @Pattern(regexp = "(?=\\S+$)[\\w가-힣a-zA-Z]{2,16}"
            , message = "닉네임: 공백이나 특수문자를 사용할 수 없으며, 2자 이상 16자 이하여야 합니다.")
    @Size(min = 2, max = 16)
    private final String nickname;

    @NotBlank(message = "아파트 동(층): 필수 정보입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,8}$"
            , message = "동(층): 영어 대소문자, 한글, 숫자만 입력 가능하며, 1자 이상 8자 이하여야 합니다.")
    @Size(min = 1, max = 8)
    private final String dong;

    @NotBlank(message = "아파트 호: 필수 정보입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,8}$"
            , message = "아파트 호: 영어 대소문자, 한글, 숫자만 입력 가능하며, 1자 이상 8자 이하여야 합니다.")
    @Size(min = 1, max = 8)
    private final String ho;

    private Long apartmentId;

    @Builder
    public JoinMemberRequest(Boolean termsService, Boolean privateInformationCollection, Boolean snsMarketingInformationReceive, String fullName, String birthFirst, String gender, String phone, String username, String password, String nickname, String dong, String ho, Long apartmentId) {
        this.termsService = termsService;
        this.privateInformationCollection = privateInformationCollection;
        this.snsMarketingInformationReceive = snsMarketingInformationReceive;
        this.fullName = fullName;
        this.birthFirst = birthFirst;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.dong = dong;
        this.ho = ho;
        this.apartmentId = apartmentId;
    }
}
