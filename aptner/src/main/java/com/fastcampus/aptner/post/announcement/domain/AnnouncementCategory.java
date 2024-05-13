package com.fastcampus.aptner.post.announcement.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",length = 10, nullable = false)
    private AnnouncementType type;

    @Column(name = "name",length = 20, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "announcementCategoryId")
    private List<Announcement> announcementList = new ArrayList<>();
}
