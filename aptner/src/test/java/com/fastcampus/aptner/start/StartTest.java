package com.fastcampus.aptner.start;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.domain.MemberStatus;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.announcement.repository.AnnouncementCategoryRepository;
import com.fastcampus.aptner.post.temp.repository.TempMemberRepository;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@SpringBootTest
@ActiveProfiles("test")
public class StartTest {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private  TempMemberRepository memberRepository;

    @Autowired
    private AnnouncementCategoryRepository announcementCategoryRepository;


    @Test
    public void InsertMember(){
        if (memberRepository.findAll().isEmpty()) {
            Member member = Member.builder()
                    .status(MemberStatus.ACTIVATE)
                    .authenticatedAt(LocalDateTime.now())
                    .phone("01012341234")
                    .authenticationStatus(true)
                    .birthFirst("990831")
                    .fullName("홍길동")
                    .gender("M")
                    .nickname("길똥")
                    .password("qweasd1234!!")
                    .username("honghong")
                    .build();
            memberRepository.save(member);
        }
    }

    @Test
    public void InsertApartment(){
        if (apartmentRepository.findAll().isEmpty()) {
            Apartment apartment = Apartment.builder()
                    .dutyTime("오전 8시~오후10시")
                    .tel("031-123-1234")
                    .sido("경기도 용인시")
                    .gugun("기흥구")
                    .banner("배너url")
                    .icon("아이콘 url")
                    .name("아파트이름")
                    .road("구성로123")
                    .zipcode("12312")
                    .build();
            apartmentRepository.save(apartment);
        }
    }
    @Test
    public void InsertAnnouncementCategory(){
        if (announcementCategoryRepository.findAll().isEmpty()){
            AnnouncementCategory announcementCategory = AnnouncementCategory.builder()
                    .type(AnnouncementType.NOTICE)
                    .apartmentId(apartmentRepository.findById(1L).orElseThrow(NoSuchElementException::new))
                    .name("전체")
                    .build();
            announcementCategoryRepository.save(announcementCategory);
        }
    }

}
