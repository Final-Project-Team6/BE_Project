package com.fastcampus.aptner.apartment.domain;

import com.fastcampus.aptner.member.domain.MemberHome;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"apartmentId"})
@Table(name = "home")
@Entity
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_id")
    private Long homeId;

    @Column(length = 50)
    private String dong;

    @Column(length = 50)
    private String ho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @JsonIgnore
    @OneToMany(mappedBy = "homeId")
    private List<MemberHome> memberHomes = new ArrayList<>();


    @Builder
    public Home(String dong, String ho) {
        this.dong = dong;
        this.ho = ho;
    }

    public void changeApartment(Apartment apartment) {
        this.apartmentId = apartment;
    }
}
