package com.fastcampus.aptner.post.information.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @Column(name = "name",length = 10)
    private String name;
}
