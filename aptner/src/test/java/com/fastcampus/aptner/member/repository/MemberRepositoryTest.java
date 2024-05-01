package com.fastcampus.aptner.member.repository;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.domain.MemberRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void InsertMember() {
        // given
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .password(passwordEncoder.encode("1111"))
                    .nickname("USER" + i)
                    .content("USER content " + i)
                    .phone("010-1234-1234")
                    .socialLogin(false)
                    .build();

            member.addRole(MemberRole.USER);

            if (i > 5) {member.addRole(MemberRole.MANAGER);}
            if (i > 8) {member.addRole(MemberRole.ADMIN);}

            memberRepository.save(member);
        }
        // expected
        Assertions.assertThat(memberRepository.count()).isEqualTo(10);
    }
}
