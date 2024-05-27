package com.fastcampus.aptner.member.dto;

import com.fastcampus.aptner.apartment.dto.HomeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberHomeDTO {
    private Long memberHomeId;
    private HomeDTO home;
}