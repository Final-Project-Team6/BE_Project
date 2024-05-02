package com.fastcampus.aptner.member.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberDTO extends User {

    private String email;
    private String password;
    private String nickname;
    private String content;
    private String phone;
    private boolean socialLogin;
    private List<String> roleNames = new ArrayList<>(); // 프론트에서 처리하기 편한 형태


    public MemberDTO(String email, String password, String nickname, String content, String phone, Boolean socialLogin, List<String> roleNames) {
        super(
                email,
                password,
                roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).toList()
        );

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.content = content;
        this.phone = phone;
        this.socialLogin = socialLogin;
        this.roleNames = roleNames;
    }


    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email", email);
        dataMap.put("password", password);
        dataMap.put("nickname", nickname);
        dataMap.put("content", content);
        dataMap.put("phone", phone);
        dataMap.put("socialLogin", socialLogin);
        dataMap.put("roleNames", roleNames);

        return dataMap;
    }
}
