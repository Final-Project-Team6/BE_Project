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

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10, nullable = false)
    private InformationType type;

    @JsonIgnore
    @OneToMany(mappedBy = "informationCategoryId")
    private List<Information> informationList = new ArrayList<>();
    @Builder
    public InformationCategory(Apartment apartmentId,InformationType type, String name) {
        this.apartmentId = apartmentId;
        this.type = type;
        this.name = name;
    }


    public static InformationCategory from(Apartment apartment, InformationDTO.InformationCategoryReqDTO dto) {
        return InformationCategory.builder()
                .apartmentId(apartment)
                .type(dto.type())
                .name(dto.name())
                .build();
    }
    public void updateCategory(InformationDTO.InformationCategoryReqDTO dto) {
        this.name = dto.name();
        this.type = dto.type();
    }

}
