package com.fastcampus.aptner.post.complaint.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
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
    private Long id;

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
}
