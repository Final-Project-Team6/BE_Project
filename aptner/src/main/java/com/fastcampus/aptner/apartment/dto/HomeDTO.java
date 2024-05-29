package com.fastcampus.aptner.apartment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeDTO {
    private Long homeId;
    private String dong;
    private String ho;
    private ApartmentDTO apartment;
}