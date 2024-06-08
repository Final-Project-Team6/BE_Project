package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.reqeust.FindPasswordRequest;
import com.fastcampus.aptner.member.dto.response.FindUsernameResponse;
import com.fastcampus.aptner.member.service.FindMemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Tag(name = "회원 정보 찾기(사용자)", description = "아이디 찾기, 비밀번호 찾기")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class FindMemberController {

    private final FindMemberServiceImpl findMemberService;

    @Operation(
            summary = "회원 아이디 찾기 API",
            description = "phone: 회원 휴대전화번호(필수)\n\n" +
                    "fullName: 회원 법적이름(필수)"
    )
    @GetMapping("/find/username")
    public ResponseEntity<?> getMemberByPhoneAndFullName(@RequestParam(name = "phone") String phone,
                                                         @RequestParam(name = "fullName") String fullName) {

        FindUsernameResponse response = findMemberService.getMemberByPhoneAndFullName(phone, fullName);

        return new ResponseEntity<>(new HttpResponse<>(1, "아아디 찾기에 성공했습니다.", response), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 비밀번호 찾기 API",
            description = "username: 회원 아이디(필수)\n\n" +
                    "fullName: 회원 법적이름(필수)\n\n" +
                    "phone: 회원 휴대전화번호(필수)"
    )
    @GetMapping("/find/password")
    public ResponseEntity<?> getMemberByPhoneAndFullName(@RequestParam(name = "username") String username,
                                                         @RequestParam(name = "fullName") String fullName,
                                                         @RequestParam(name = "phone") String phone) {

        Long memberId = findMemberService.getMemberByUserNameAndFullNameAndPhone(username, fullName, phone);
        UUID memberIdUUID = UUID.nameUUIDFromBytes(memberId.toString().getBytes(StandardCharsets.UTF_8));

        return new ResponseEntity<>(new HttpResponse<>(1, "비밀번호 찾기에 성공했습니다.", List.of("memberId: " + memberId, "memberIdUUID: " + memberIdUUID)), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 비밀번호 변경하기 API",
            description = "memberId: 회원 고유 식별 번호(필수)\n\n" +
                    "password: 회원 새로운 비밀번호(필수)\n\n" +
                    "memberIdUUID: 회원 비밀번호 찾기 API 에서 응답 받은 UUID(필수)"
    )
    @PatchMapping("/find/password")
    public ResponseEntity<?> modifiedPasswordByMemberId(@RequestBody @Valid FindPasswordRequest request,
                                                        BindingResult bindingResult) {
        findMemberService.modificationPassword(request.getMemberId(), request.getPassword(), request.getMemberIdUUID());

        return new ResponseEntity<>(new HttpResponse<>(1, "비밀번호 변경에 성공했습니다.", null), HttpStatus.OK);
    }


}
