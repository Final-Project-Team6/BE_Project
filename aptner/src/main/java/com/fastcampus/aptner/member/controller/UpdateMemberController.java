package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.member.dto.response.DetailsMemberResponse;
import com.fastcampus.aptner.member.dto.response.HttpMemberResponse;
import com.fastcampus.aptner.member.dto.request.UpdateMemberDetailsRequest;
import com.fastcampus.aptner.member.dto.request.UpdateMemberPasswordRequest;
import com.fastcampus.aptner.member.service.UpdateMemberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/member")
@RestController
public class UpdateMemberController {

    UpdateMemberService updateMemberService;

    @GetMapping("/details/{id}")
    public HttpMemberResponse<DetailsMemberResponse> getUserInfo(@PathVariable(name = "id") Long id) {
        return HttpMemberResponse.of(
                HttpStatus.OK,
                "회원 정보 조회를 성공했습니다.",
                updateMemberService.findMemberDetails(id));
    }

    @PostMapping("/details/{id}/password")
    public HttpMemberResponse<String> modifyUserPassword(@PathVariable(name = "id") Long id,
                                                  @Valid @RequestBody UpdateMemberPasswordRequest response) {
        updateMemberService.updateMemberPassword(id, response);

        return HttpMemberResponse.of(HttpStatus.OK, "회원 비밀번호가 변경되었습니다.", null);
    }

    @PostMapping("/details/{id}/info")
    public HttpMemberResponse<String> modifyUserInfo(@PathVariable("id") Long id,
                                              @Valid @RequestBody UpdateMemberDetailsRequest response) {
        updateMemberService.updateMemberDetails(id, response);

        return HttpMemberResponse.of(HttpStatus.OK, "회원 정보 변경이 완료되었습니다.", null);
    }

    @PostMapping("/delete/{id}")
    public HttpMemberResponse<String> deleteUser(@PathVariable("id") Long id,
                                          @RequestBody String password) {
        updateMemberService.deleteMember(id, password);

        return HttpMemberResponse.of(HttpStatus.OK, "회원 탈퇴에 성공했습니다.", null);
    }

}
