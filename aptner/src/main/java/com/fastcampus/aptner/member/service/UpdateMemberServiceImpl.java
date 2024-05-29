package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.domain.Home;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.apartment.repository.HomeRepository;
import com.fastcampus.aptner.global.handler.exception.CustomDataNotFoundException;
import com.fastcampus.aptner.global.handler.exception.CustomDuplicationKeyException;
import com.fastcampus.aptner.member.domain.*;
import com.fastcampus.aptner.member.dto.SubscriptionDTO;
import com.fastcampus.aptner.member.dto.reqeust.InsertMemberHomeRequest;
import com.fastcampus.aptner.member.dto.reqeust.UpdateMemberProfileRequest;
import com.fastcampus.aptner.member.repository.MemberHomeRepository;
import com.fastcampus.aptner.member.repository.MemberRepository;
import com.fastcampus.aptner.member.repository.MemberRoleRepository;
import com.fastcampus.aptner.member.repository.SubscriptionRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fastcampus.aptner.member.domain.QMemberRole.memberRole;

@RequiredArgsConstructor
@Service
public class UpdateMemberServiceImpl implements UpdateMemberService {

    private final MemberRepository memberRepository;
    private final ApartmentRepository apartmentRepository;
    private final HomeRepository homeRepository;
    private final MemberHomeRepository memberHomeRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final JPAQueryFactory jpaQueryFactory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void updateAuthenticationStatusById(Long memberId, Boolean authenticationStatus) { // JPQL
        int updatedRows = memberRepository.updateAuthenticationStatusById(memberId, authenticationStatus);
        if (updatedRows == 0) {
            throw new CustomDataNotFoundException("회원이 존재하지 않습니다.");
        }
    }

    @Transactional
    @Override
    public void updateNicknameById(Long memberId, String nickname) { // JPQL
        int updatedRows = memberRepository.updateNicknameById(memberId, nickname);
        if (updatedRows == 0) {
            throw new CustomDataNotFoundException("회원이 존재하지 않습니다.");
        }
    }

    @Transactional
    @Override
    public void updatePhoneById(Long memberId, String phone) {
        int updatedRows = memberRepository.updatePhoneById(memberId, phone);
        if (updatedRows == 0) {
            throw new CustomDataNotFoundException("회원이 존재하지 않습니다.");
        }
    }

    @Transactional
    @Override
    public void updatePasswordById(Long memberId, String password) {
        String encodePassword = bCryptPasswordEncoder.encode(password);

        int updatedRows = memberRepository.updatePasswordById(memberId, encodePassword);
        if (updatedRows == 0) {
            throw new CustomDataNotFoundException("회원이 존재하지 않습니다.");
        }
    }

    @Transactional
    @Override
    public void updateMemberRoleByMemberIdAndApartmentId(Long memberId, Long apartmentId, String newRoleName) {
        jpaQueryFactory.update(memberRole)
                .set(memberRole.roleName, RoleName.valueOf(newRoleName))
                .where(memberRole.memberId.memberId.eq(memberId)
                        .and(memberRole.apartmentId.apartmentId.eq(apartmentId)))
                .execute();
    }


    @Transactional
    @Override
    public void insertMemberHomeByMemberIdAndApartmentId(Long memberId, InsertMemberHomeRequest request) {
        System.out.println("request.getApartmentId() = " + request.getApartmentId());
        
        Apartment findApartment = apartmentRepository.findApartmentByApartmentId(request.getApartmentId())
                .orElseThrow(() -> new CustomDataNotFoundException("아파트가 존재하지 않습니다."));

        Member findMember = memberRepository.findMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        List<MemberHome> byMemberId = memberHomeRepository.findAllByMemberId(findMember);

        for (MemberHome memberHome : byMemberId) {
            if (memberHome.getHomeId().getDong().equals(request.getDong()) && memberHome.getHomeId().getHo().equals(request.getHo())) {
                throw new CustomDuplicationKeyException("이미 등록된 자택입니다.");
            }
        }

        Home home = Home.builder()
                .dong(request.getDong())
                .ho(request.getHo())
                .build();

        // 자택 - 아파트 추가하기
        home.changeApartment(findApartment);
        Home savedHome = homeRepository.save(home);

        MemberHome memberHome = MemberHome.builder()
                .memberId(findMember)
                .homeId(savedHome)
                .build();

        // 회원 - 자택 연관 관계 설정 및 저장하기
        memberHomeRepository.save(memberHome);

        // 기본 권한 설정 및 저장하기
        MemberRole memberRole = MemberRole.of(RoleName.USER, findMember, findApartment);
        memberRoleRepository.save(memberRole);
    }

    @Transactional
    @Override
    public void deleteMemberHomeByMemberIdAndHomeId(Long memberId, Long homeId) {
        Member findMember = memberRepository.findMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        Home findHome = homeRepository.findHomeByHomeId(homeId)
                .orElseThrow(() -> new CustomDataNotFoundException("자택이 존재하지 않습니다."));


        MemberHome memberHome = memberHomeRepository.findByMemberIdAndHomeId(findMember, findHome)
                .orElseThrow(() -> new CustomDataNotFoundException("자택이 존재하지 않습니다."));

        Home home = memberHome.getHomeId();
        Apartment apartment = home.getApartmentId();

        memberHomeRepository.delete(memberHome);
        memberRoleRepository.deleteByMemberIdAndApartmentId(findMember, apartment);
        homeRepository.delete(home);
    }

    @Transactional
    @Override
    public void updateMemberHomeDongHo(Long memberId, Long homeId, String newDong, String newHo) {
        Member findMember = memberRepository.findMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        Home findHome = homeRepository.findHomeByHomeId(homeId)
                .orElseThrow(() -> new CustomDataNotFoundException("자택이 존재하지 않습니다."));

        MemberHome memberHome = memberHomeRepository.findByMemberIdAndHomeId(findMember, findHome)
                .orElseThrow(() -> new CustomDataNotFoundException("해당 자택이 존재하지 않습니다."));

        Home home = memberHome.getHomeId();
        home.changeDong(newDong);
        home.changeHo(newHo);

        homeRepository.save(home);
    }

    @Transactional
    @Override
    public void updateMemberStatus(Long memberId, String status) {
        Member findMember = memberRepository.findMemberByMemberId(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        findMember.updateMemberStatus(status);
    }

    @Transactional
    @Override
    public void updateMemberSubscription(Long memberId, SubscriptionDTO subscriptionDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        Subscription subscription = subscriptionRepository.findByMemberId(member)
                .orElseThrow(() -> new CustomDataNotFoundException("회원의 구독 정보가 존재하지 않습니다."));

        subscription.changeTermsService(subscriptionDTO.getTermsService());
        subscription.changePrivateInformationCollection(subscriptionDTO.getPrivateInformationCollection());
        subscription.changeSnsMarketingInformationReceive(subscriptionDTO.getSnsMarketingInformationReceive());

        subscriptionRepository.save(subscription);
    }

    @Transactional(readOnly = true)
    @Override
    public SubscriptionDTO getMemberSubscription(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        Subscription subscription = subscriptionRepository.findByMemberId(member)
                .orElseThrow(() -> new CustomDataNotFoundException("회원의 구독 정보가 존재하지 않습니다."));

        return new SubscriptionDTO(
                subscription.getTermsService(),
                subscription.getPrivateInformationCollection(),
                subscription.getSnsMarketingInformationReceive()
        );
    }

    @Override
    public void updateMemberProfileImage(Long memberId, String profileImage) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        member.updateMemberProfileImage(profileImage);

        memberRepository.save(member);
    }
}
