package com.fastcampus.aptner.post.information.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "information_category")
@Entity
public class InformationCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "informationCategory_id")
    private Long informationCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @Column(name = "name",length = 10)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "informationCategoryId")
    private List<Information> informationList = new ArrayList<>();
    @Builder
    public InformationCategory(Apartment apartmentId, String name) {
        this.apartmentId = apartmentId;
        this.name = name;
    }


    public static InformationCategory from(Apartment apartment, InformationDTO.InformationCategoryReqDTO dto) {
        return InformationCategory.builder()
                .apartmentId(apartment)
                .name(dto.name())
                .build();
    }
    public void updateCategory(InformationDTO.InformationCategoryReqDTO dto) {
        this.name = dto.name();
    }

}
