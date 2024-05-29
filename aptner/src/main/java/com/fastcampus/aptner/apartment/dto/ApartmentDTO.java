package com.fastcampus.aptner.apartment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDTO {
    private Long apartmentId;
    private String name;
    private String sido;
    private String gugun;
    private String road;
    private String zipcode;
    private String icon;
    private String banner;
    private String tel;
    private String dutyTime;
}