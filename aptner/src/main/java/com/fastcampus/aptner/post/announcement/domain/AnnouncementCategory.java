package com.fastcampus.aptner.post.announcement.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "announcement_category")
@Entity
public class AnnouncementCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_category_id")
    private Long announcementCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10, nullable = false)
    private AnnouncementType type;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "announcementCategoryId")
    private List<Announcement> announcementList = new ArrayList<>();

    @Builder
    public AnnouncementCategory(Apartment apartmentId, AnnouncementType type, String name) {
        this.apartmentId = apartmentId;
        this.type = type;
        this.name = name;
    }

    public static AnnouncementCategory from(Apartment apartment, AnnouncementDTO.AnnouncementCategoryReqDTO dto) {
        return AnnouncementCategory.builder()
                .apartmentId(apartment)
                .type(dto.type())
                .name(dto.name())
                .build();
    }

    public void updateCategory(AnnouncementDTO.AnnouncementCategoryReqDTO dto) {
        this.name = dto.name();
        this.type = dto.type();
    }


}
