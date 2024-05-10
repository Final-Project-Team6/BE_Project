package com.fastcampus.aptner.post.communication.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @Column(name = "name",length = 50)
    private String name;
}
