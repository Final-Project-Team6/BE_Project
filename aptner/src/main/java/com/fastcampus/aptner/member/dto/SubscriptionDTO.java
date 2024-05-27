package com.fastcampus.aptner.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private Boolean termsService;
    private Boolean privateInformationCollection;
    private Boolean snsMarketingInformationReceive;
}