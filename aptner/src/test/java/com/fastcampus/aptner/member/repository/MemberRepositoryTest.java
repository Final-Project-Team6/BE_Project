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
            Member createdMember = Member.builder()
                    .email("member" + i + "@gmail.com")
                    .password(passwordEncoder.encode("1111"))
                    .nickname("USER" + i)
                    .content(i + "동 반포자이")
                    .phone("010-1234-1234")
                    .socialLogin(false)
                    .build();

            createdMember.addRole(MemberRole.USER);

            if (i > 5) {createdMember.addRole(MemberRole.MANAGER);}
            if (i > 8) {createdMember.addRole(MemberRole.ADMIN);}

            memberRepository.save(createdMember);
        }
        // expected
        Assertions.assertThat(memberRepository.count()).isEqualTo(10);
    }
}
