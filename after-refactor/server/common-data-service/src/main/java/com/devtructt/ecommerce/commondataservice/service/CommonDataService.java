package com.devtructt.ecommerce.commondataservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.devtructt.ecommerce.commondataservice.dto.BrandImageDto;
import com.devtructt.ecommerce.commondataservice.dto.CarouselImageDto;
import com.devtructt.ecommerce.commondataservice.dto.GenderApparelImageDto;
import com.devtructt.ecommerce.commondataservice.dto.ProductFilterDto;
import com.devtructt.ecommerce.commondataservice.dto.ProductFiltersByGenderDto;
import com.devtructt.ecommerce.commondataservice.dto.request.ProductFiltersRequest;
import com.devtructt.ecommerce.commondataservice.dto.response.HomepageImagesResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductFilterCombinationResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductFiltersByGenderResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductFiltersByOtherProductFiltersResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductsByIdResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductsWithTotalCountResponse;
import com.devtructt.ecommerce.commondataservice.entity.Gender;
import com.devtructt.ecommerce.commondataservice.entity.Product;
import com.devtructt.ecommerce.commondataservice.enums.SortOption;
import com.devtructt.ecommerce.commondataservice.repository.ApparelRepository;
import com.devtructt.ecommerce.commondataservice.repository.BrandImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.BrandRepository;
import com.devtructt.ecommerce.commondataservice.repository.CarouselImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.GenderApparelImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.GenderRepository;
import com.devtructt.ecommerce.commondataservice.repository.ProductRepository;
import com.devtructt.ecommerce.commondataservice.specification.ProductSpecification;
import com.devtructt.ecommerce.commondataservice.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonDataService {
	private final ProductRepository productRepository;

	private final GenderRepository genderRepository;
	private final ApparelRepository apparelRepository;
	private final BrandRepository brandRepository;

	private final GenderApparelImageRepository genderApparelImageRepository;
	private final BrandImageRepository brandImageRepository;
	private final CarouselImageRepository carouselImageRepository;

	private final ModelMapperUtil modelMapperUtil;

	@Cacheable(value = "productsByIds", key = "#ids")
	public ProductsByIdResponse getProductsByIds(List<Long> ids) {
		List<Product> products = productRepository.findAllById(ids);
		HashMap<Long, Product> productById = new HashMap<>();
		products.forEach(product -> productById.put(product.getId(), product));
		return new ProductsByIdResponse(productById);
	}

	@Cacheable(value = "productsByProductFilters", key = "#productFiltersRequest")
	public ProductsWithTotalCountResponse getProductsByProductFilders(ProductFiltersRequest productFiltersRequest) {
		Specification<Product> specification = Specification
				.where(ProductSpecification.withGenderIds(productFiltersRequest.getGenderIds()))
				.and(ProductSpecification.withApparelIds(productFiltersRequest.getApparelIds()))
				.and(ProductSpecification.withBrandIds(productFiltersRequest.getBrandIds()))
				.and(ProductSpecification.priceBetween(productFiltersRequest.getMinPrice(),
						productFiltersRequest.getMaxPrice()))
				.and(ProductSpecification.nameContains(productFiltersRequest.getProductName()));

		Pageable pageable = PageRequest.of(
				productFiltersRequest.getPageNumber() != null ? productFiltersRequest.getPageNumber() : 0,
				productFiltersRequest.getPageSize() != null ? productFiltersRequest.getPageSize() : 20,
				getSortFromSortOption(productFiltersRequest.getSortOption()));

		Page<Product> page = productRepository.findAll(specification, pageable);
		return new ProductsWithTotalCountResponse(page.getTotalElements(), page.getContent());
	}

	private Sort getSortFromSortOption(SortOption sortOption) {
		if (sortOption == null) {
			return Sort.by(Sort.Direction.DESC, "rating");
		}
		return switch (sortOption) {
			case NEWEST -> Sort.by(Sort.Direction.DESC, "publicationDate");
			case POPULARITY -> Sort.by(Sort.Direction.DESC, "rating");
			case PRICE_LOW_TO_HIGH -> Sort.by(Sort.Direction.ASC, "price");
			case PRICE_HIGH_TO_LOW -> Sort.by(Sort.Direction.DESC, "price");
			default -> Sort.by(Sort.Direction.DESC, "rating");
		};
	}

	@Cacheable(value = "commonApi", key = "homepageImages")
	public HomepageImagesResponse getHomepageImages() {
		List<GenderApparelImageDto> genderApparelImageDtos = modelMapperUtil.mapList(genderApparelImageRepository.findAll(), GenderApparelImageDto.class);
		List<BrandImageDto> brandImageDtos = modelMapperUtil.mapList(brandImageRepository.findAll(), BrandImageDto.class);
		List<CarouselImageDto> carouselImageDtos = modelMapperUtil.mapList(carouselImageRepository.findAll(), CarouselImageDto.class);
		return new HomepageImagesResponse(genderApparelImageDtos, brandImageDtos, carouselImageDtos);
	}

	@Cacheable(value = "commonApi", key = "#productFiltersByGender")
	public ProductFiltersByGenderResponse getProductFiltersByGender() {
		List<Gender> genders = genderRepository.findAll();
		List<ProductFiltersByGenderDto> productFiltersByGenderDtos = new ArrayList<>();
		for (Gender gender: genders) {
			List<ProductFilterDto> apparels = productRepository.findBrandsByGender(gender.getId(), PageRequest.of(0, 10));
			List<ProductFilterDto> brands = productRepository.findApparelsByGender(gender.getId(), PageRequest.of(0, 10));
			productFiltersByGenderDtos.add(new ProductFiltersByGenderDto(gender.getId(), gender.getName(), brands, apparels));
		}
		return new ProductFiltersByGenderResponse(productFiltersByGenderDtos);
	}

	@Cacheable(value = "productFiltersByOtherProductFilters", key = "#productFiltersRequest")
	public ProductFiltersByOtherProductFiltersResponse getProductFiltersByOtherProductFilters(ProductFiltersRequest productFiltersRequest) {
		List<ProductFilterDto> genders = productRepository.findGendersByOtherProductFilters(
				productFiltersRequest.getApparelIds(),
				productFiltersRequest.getBrandIds(),
				productFiltersRequest.getProductName(),
				productFiltersRequest.getMinPrice(),
				productFiltersRequest.getMaxPrice());
		List<ProductFilterDto> apparels = productRepository.findApparelsByOtherProductFilters(
				productFiltersRequest.getGenderIds(),
				productFiltersRequest.getBrandIds(),
				productFiltersRequest.getProductName(),
				productFiltersRequest.getMinPrice(),
				productFiltersRequest.getMaxPrice());
		List<ProductFilterDto> brands = productRepository.findBrandsByByOtherProductFilters(
				productFiltersRequest.getGenderIds(),
				productFiltersRequest.getApparelIds(),
				productFiltersRequest.getProductName(),
				productFiltersRequest.getMinPrice(),
				productFiltersRequest.getMaxPrice());
		return new ProductFiltersByOtherProductFiltersResponse(
				genders,
				apparels,
				brands,
				productFiltersRequest.getMinPrice(),
				productFiltersRequest.getMaxPrice(),
				productFiltersRequest.getSortOption());
	}

	public ProductFilterCombinationResponse getProductFilterCombinations() {
		return new ProductFilterCombinationResponse(
				genderRepository.findAll(),
				apparelRepository.findAll(),
				brandRepository.findAll(),
				productRepository.findDistinctGenderApparelCombinations(),
				productRepository.findDistinctGenderBrandCombinations(),
				productRepository.findDistinctApparelBrandCombinations(),
				productRepository.findDistinctGenderApparelBrandCombinations(),
				productRepository.findDistinctProductNames());
	}
}