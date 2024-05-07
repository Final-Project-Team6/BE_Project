package com.fastcampus.aptner.member.dto.request;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.domain.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class JoinMemberRequest {

    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "영문/숫자 6~20자 이내로 작성해주세요")
    @NotBlank(message = "아이디는 반드시 입력해야 합니다.") // null 혹은 공백 불가능.
    private String username; // 회원 아이디

    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}"
            , message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 조합하여 작성해야 합니다.")
    private String password; // 회원 비밀번호

    @NotBlank(message = "닉네임은 반드시 입력해야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요")
    private String nickname; // 회원 닉네임

    @NotBlank(message = "이름은 반드시 입력해야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~16자 이내로 작성해주세요")
    private String fullName; // 회원 이름

    @NotBlank(message = "전화번호는 반드시 입력해야 합니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "010-1234-5678 형식으로 작성해주세요")
    private String phone; // 회원 전화번호

    public Member toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname)
                .fullName(fullName)
                .phone(phone)
                .memberRole(MemberRole.RESIDENT)
                .build();
    }
}
