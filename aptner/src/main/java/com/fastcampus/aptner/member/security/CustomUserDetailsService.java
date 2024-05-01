package com.fastcampus.aptner.member.security;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.MemberDTO;
import com.fastcampus.aptner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.getWithRoles(username).orElseThrow();

        return new MemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                member.getContent(),
                member.getPhone(),
                member.getSocialLogin(),
                member.getMemberRoleList()
                        .stream().map(Enum::name).toList()
        );
    }
}
