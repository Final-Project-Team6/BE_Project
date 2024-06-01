package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.domain.Home;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.apartment.repository.HomeRepository;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.global.handler.exception.CustomDuplicationKeyException;
import com.fastcampus.aptner.member.domain.*;
import com.fastcampus.aptner.member.dto.reqeust.JoinMemberRequest;
import com.fastcampus.aptner.member.repository.MemberRepository;
import com.fastcampus.aptner.member.repository.MemberRoleRepository;
import com.fastcampus.aptner.member.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class JoinMemberServiceImpl implements JoinMemberService {

    private final MemberRepository memberRepository;
    private final HomeRepository homeRepository;
    private final ApartmentRepository apartmentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String NICKNAME_PATTERN = "^[a-zA-Z0-9가-힣]{2,16}$";
    private static final String PHONE_PATTERN = "^[0-9]{10,11}$";
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{5,20}$";



    @Override
    public void checkMemberPhoneDuplication(String phone) {
        Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
        Matcher phoneMatcher = phonePattern.matcher(phone);

        if (phone.isEmpty()) {
            throw new CustomAPIException("휴대전화번호는 필수 입력입니다.");
        }

        if (!phoneMatcher.matches()) {
            throw new CustomAPIException("잘못된 휴대전화번호 입니다.");
        }

        if (memberRepository.existsByPhone(phone)) {
            throw new CustomDuplicationKeyException("이미 존재하는 휴대전화번호 입니다.");
        }
    }

    @Override
    public void checkMemberNickNameDuplication(String nickname) {
        Pattern nicknamePattern = Pattern.compile(NICKNAME_PATTERN);
        Matcher nicknameMatcher = nicknamePattern.matcher(nickname);

        if (nickname.isEmpty()) {
            throw new CustomAPIException("닉네임은 필수 입력입니다.");
        }

        if (!nicknameMatcher.matches()) {
            throw new CustomAPIException("잘못된 닉네임 입니다.");
        }
        
        if (memberRepository.existsByNickname(nickname)) {
            throw new CustomDuplicationKeyException("이미 존재하는 닉네임 입니다.");
        }
    }

    @Override
    public void checkMemberUsernameDuplication(String username) {
        Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
        Matcher usernameMatcher = usernamePattern.matcher(username);

        if (username.isEmpty()) {
            throw new CustomAPIException("아이디는 필수 입력입니다.");
        }

        if (!usernameMatcher.matches()) {
            throw new CustomAPIException("잘못된 아이디 입니다.");
        }

        if (memberRepository.existsByUsername(username)) {
            throw new CustomDuplicationKeyException("이미 존재하는 아이디 입니다.");
        }
    }



    // TODO: 로직 분리가 가능해 보인다. 리팩토링 고민해보자.
    @Transactional
    public void joinMember(JoinMemberRequest request) {

        checkMemberPhoneDuplication(request.getPhone());
        checkMemberNickNameDuplication(request.getNickname());
        checkMemberUsernameDuplication(request.getUsername());

        // Member 엔티티 생성하기
        Member member = Member.builder()
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .birthFirst(request.getBirthFirst())
                .nickname(request.getNickname())
                .fullName(request.getFullName())
                .gender(request.getGender())
                .build();

        // Home 엔티티 생성 & 저장하기
        Home home = Home.builder()
                .dong(request.getDong())
                .ho(request.getHo())
                .build();

        homeRepository.save(home);

        // TODO: 예외 생성하기
        // Apartment 엔티티 가져오기
        Apartment apartment = apartmentRepository.findApartmentByApartmentId(request.getApartmentId())
                .orElseThrow(() -> new CustomAPIException("아파트가 존재하지 않습니다."));

        // [연관 관계 메서드]: Home, Apartment
        home.changeApartment(apartment);

        // MemberHome 엔티티 설정하기
        MemberHome memberHome = MemberHome.builder()
                .homeId(home)
                .memberId(member)
                .build();

        // [연관 관계 메서드]: Member, MemberHome
        member.addMemberHome(memberHome);

        // Subscription 엔티티 생성하기
        Subscription subscription = Subscription.builder()
                .termsService(request.getTermsService())
                .privateInformationCollection(request.getPrivateInformationCollection())
                .snsMarketingInformationReceive(request.getSnsMarketingInformationReceive())
                .build();
        subscriptionRepository.save(subscription);

        // [연관 관계 메서드]: Member, Subscription
        member.addMemberSubscription(subscription);

        // MemberRole 엔티티 생성하기
        MemberRole memberRole = MemberRole.builder()
                .roleName(RoleName.USER)
                .build();

        memberRoleRepository.save(memberRole);

        // [연관 관계 메서드]: Member, MemberRole
        member.addMemberRole(memberRole, apartment);

        // Member 엔티티 저장하기
        memberRepository.save(member);
    }
}
