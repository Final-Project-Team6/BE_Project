package com.fastcampus.aptner.post.complaint.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "complaint_category")
@Entity
public class ComplaintCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_category_id")
    private Long complaintCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",length = 10, nullable = false)
    private ComplaintType type;

    @Column(name = "name",length = 20, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "complaintCategoryId")
    private List<Complaint> complaintList = new ArrayList<>();
    @Builder
    public ComplaintCategory(Apartment apartmentId, ComplaintType type, String name) {
        this.apartmentId = apartmentId;
        this.type = type;
        this.name = name;
    }

    public static ComplaintCategory from(ComplaintDTO.ComplaintCategoryReqDTO dto, Apartment apartment){
        return ComplaintCategory.builder()
                .apartmentId(apartment)
                .type(dto.type())
                .name(dto.name())
                .build();
    }

    public void updateComplaintCategory(ComplaintDTO.ComplaintCategoryReqDTO dto){
        this.name = dto.name();
        this.type = dto.type();
    }
}
