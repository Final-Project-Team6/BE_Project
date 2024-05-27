package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.domain.Home;
import com.fastcampus.aptner.apartment.dto.ApartmentDTO;
import com.fastcampus.aptner.apartment.dto.HomeDTO;
import com.fastcampus.aptner.global.handler.exception.CustomDataNotFoundException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.domain.MemberHome;
import com.fastcampus.aptner.member.dto.MemberHomeDTO;
import com.fastcampus.aptner.member.repository.MemberHomeRepository;
import com.fastcampus.aptner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberHomeServiceImpl implements MemberHomeService {

    private final MemberHomeRepository memberHomeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Boolean existsByMemberIdAndApartmentId(Long memberId, Long apartmentId) {
        return memberHomeRepository.existsByMemberIdAndApartmentId(memberId, apartmentId);
    }

    @Transactional
    @Override
    public List<MemberHomeDTO> findAllHomesByMemberId(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        List<MemberHome> memberHomes = memberHomeRepository.findAllByMemberId(findMember);

        return memberHomes.stream()
                .map(this::getMemberHomeDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MemberHomeDTO findHomeByMemberIdAndMemberHomeId(Long memberId, Long memberHomeId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        MemberHome memberHome = memberHomeRepository.findByMemberIdAndMemberHomeId(findMember, memberHomeId)
                .orElseThrow(() -> new CustomDataNotFoundException("자택이 존재하지 않거나 해당 회원의 자택이 아닙니다."));

        Home home = memberHome.getHomeId();
        HomeDTO homeDTO = getHomeDTO(home);
        return new MemberHomeDTO(memberHome.getMemberHomeId(), homeDTO);
    }

    private static HomeDTO getHomeDTO(Home home) {
        Apartment apartment = home.getApartmentId();
        ApartmentDTO apartmentDTO = new ApartmentDTO(
                apartment.getApartmentId(),
                apartment.getName(),
                apartment.getSido(),
                apartment.getGugun(),
                apartment.getRoad(),
                apartment.getZipcode(),
                apartment.getIcon(),
                apartment.getBanner(),
                apartment.getTel(),
                apartment.getDutyTime()
        );

        return new HomeDTO(
                home.getHomeId(),
                home.getDong(),
                home.getHo(),
                apartmentDTO
        );
    }

    private MemberHomeDTO getMemberHomeDTO(MemberHome memberHome) {
        Home home = memberHome.getHomeId();
        HomeDTO homeDTO = getHomeDTO(home);
        return new MemberHomeDTO(memberHome.getMemberHomeId(), homeDTO);
    }

}
