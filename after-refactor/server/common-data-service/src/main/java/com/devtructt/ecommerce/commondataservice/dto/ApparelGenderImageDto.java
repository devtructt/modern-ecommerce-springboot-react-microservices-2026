package com.devtructt.ecommerce.commondataservice.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ApparelGenderImageDto {
    private String title;

    private String localImagePath;

    private String imageUrl;

    private ApparelDto apparelDto;

    private GenderDto genderDto;
}