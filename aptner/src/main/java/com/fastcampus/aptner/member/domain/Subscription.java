package com.fastcampus.aptner.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "subscription")
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long subscriptionId;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "terms_service")
    private boolean termsService;

    @Column(name = "private_information_collection")
    private boolean privateInformationCollection;

    @Column(name = "sns_marketing_information_receivce")
    private boolean snsMarketingInformationReceive;

    @Column(name = "terms_service_agreement_modified_at")
    private LocalDateTime termsServiceAgreementModifiedAt;

    @Column(name = "private_information_collection_modified_at")
    private LocalDateTime privateInformationCollectionModifiedAt;

    @Column(name = "sns_marketing_information_receive_modified_at")
    private LocalDateTime snsMarketingInformationReceiveModifiedAt;



}
