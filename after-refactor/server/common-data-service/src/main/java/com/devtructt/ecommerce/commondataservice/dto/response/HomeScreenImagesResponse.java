package com.devtructt.ecommerce.commondataservice.dto.response;

import java.util.List;

import com.devtructt.ecommerce.commondataservice.dto.ApparelGenderImageDto;
import com.devtructt.ecommerce.commondataservice.dto.BrandImageDto;
import com.devtructt.ecommerce.commondataservice.dto.CarouselImageDto;

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
public class HomeScreenImagesResponse {
	private List<ApparelGenderImageDto> apparelImageDtos;
    private List<BrandImageDto> brandImageDtos;
    private List<CarouselImageDto> carouselImageDtos;
}