package com.fastcampus.aptner.post.communication.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "communication_category")
@Entity
public class CommunicationCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communication_category_id")
    private Long communicationCategoryId;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @Column(name = "name",length = 50)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "communicationCategoryId")
    private List<Communication> communicationList = new ArrayList<>();
    @Builder
    public CommunicationCategory(Apartment apartmentId, String name) {
        this.apartmentId = apartmentId;
        this.name = name;
    }

    public static CommunicationCategory from(Apartment apartment, CommunicationDTO.CommunicationCategoryReqDTO dto) {
        return CommunicationCategory.builder()
                .apartmentId(apartment)
                .name(dto.name())
                .build();
    }
    public void updateCategory(CommunicationDTO.CommunicationCategoryReqDTO dto) {
        this.name = dto.name();
    }
}
