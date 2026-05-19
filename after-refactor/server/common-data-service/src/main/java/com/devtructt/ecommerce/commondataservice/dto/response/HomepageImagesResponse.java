package com.devtructt.ecommerce.commondataservice.dto.response;

import java.util.List;

import com.devtructt.ecommerce.commondataservice.dto.BrandImageDto;
import com.devtructt.ecommerce.commondataservice.dto.CarouselImageDto;
import com.devtructt.ecommerce.commondataservice.dto.GenderApparelImageDto;

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
public class HomepageImagesResponse {
	private List<GenderApparelImageDto> genderApparelImageDto;
    private List<BrandImageDto> brandImageDtos;
    private List<CarouselImageDto> carouselImageDtos;
}