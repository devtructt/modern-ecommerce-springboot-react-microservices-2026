package com.devtructt.ecommerce.commondataservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.devtructt.ecommerce.commondataservice.dto.ProductDto;
import com.devtructt.ecommerce.commondataservice.dto.request.ProductFiltersRequest;
import com.devtructt.ecommerce.commondataservice.dto.response.FilterAttributesResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.HomeTabsDataResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.SearchSuggestionResponse;
import com.devtructt.ecommerce.commondataservice.entity.Product;
import com.devtructt.ecommerce.commondataservice.enums.SortOption;
import com.devtructt.ecommerce.commondataservice.repository.ApparelRepository;
import com.devtructt.ecommerce.commondataservice.repository.BrandImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.BrandRepository;
import com.devtructt.ecommerce.commondataservice.repository.CarouselImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.GenderApparelImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.GenderRepository;
import com.devtructt.ecommerce.commondataservice.repository.ProductRepository;
import com.devtructt.ecommerce.commondataservice.repository.SortOptionRepository;
import com.devtructt.ecommerce.commondataservice.specification.ProductSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonDataService {
    private final ProductRepository productRepository;

    private final GenderRepository genderRepository;
    private final ApparelRepository apparelRepository;
    private final BrandRepository brandRepository;
    
    private final SortOptionRepository sortOptionRepository;

    private final GenderApparelImageRepository genderApparelImageRepository;
    private final BrandImageRepository brandImageRepository;
    private final CarouselImageRepository carouselImageRepository;
    
	@Cacheable(key = "#queryParams", value = "hashMap")
    public HashMap<Long, Product> getProductsByIds(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        HashMap<Long, Product> productById = new HashMap<>();
        products.forEach(product -> productById.put(product.getId(), product));
        return productById;
    }
    
    @Cacheable(key = "#queryParams", value = "productInfoDTO")
    public ProductDto getProductsByProductFilders(ProductFiltersRequest productFiltersRequest) {
        Specification<Product> specification = Specification
        		.where(ProductSpecification.withGenderIds(productFiltersRequest.getGenderIds()))
                .and(ProductSpecification.withApparelIds(productFiltersRequest.getApparelIds()))
                .and(ProductSpecification.withBrandIds(productFiltersRequest.getBrandIds()))
                .and(ProductSpecification.priceBetween(productFiltersRequest.getMinPrice(), productFiltersRequest.getMaxPrice()))
                .and(ProductSpecification.nameContains(productFiltersRequest.getProductName()));

        Pageable pageable = PageRequest.of(
        		productFiltersRequest.getPageNumber() != null ? productFiltersRequest.getPageNumber() : 0,
				productFiltersRequest.getPageSize() != null ? productFiltersRequest.getPageSize() : 20,
				getSortFromSortOption(productFiltersRequest.getSortOption())
        );

        Page<Product> page = productRepository.findAll(specification, pageable);
        return new ProductDto(page.getTotalElements(), page.getContent());
    }

	private Sort getSortFromSortOption(SortOption sortOption) {
		if (sortOption == null) {
			return Sort.by(Sort.Direction.DESC, "rating"); // Mặc định sort theo rating nếu không có sort option
		}
		return switch (sortOption) {
			case NEWEST -> Sort.by(Sort.Direction.DESC, "publicationDate");
			case POPULARITY -> Sort.by(Sort.Direction.DESC, "viewCount");
			case PRICE_LOW_TO_HIGH -> Sort.by(Sort.Direction.ASC, "price");
			case PRICE_HIGH_TO_LOW -> Sort.by(Sort.Direction.DESC, "price");
			default -> Sort.by(Sort.Direction.DESC, "rating");
		};
	}
    
    @Cacheable(key = "#apiName", value = "mainScreenResponse")
    public MainScreenResponse getHomeScreenImages(String apiName) {
        List<ApparelImages> apparelImagesList = apparelImagesRepository.findAll();
        List<BrandImages> brandImagesList = brandImagesRepository.findAll();
        List<CarouselImages> carouselImagesList = carouselImagesRepository.findAll();

        return new MainScreenResponse(apparelImagesList, brandImagesList, carouselImagesList);
    }
    
    @Cacheable(key = "#apiName", value = "homeTabsDataResponse")
    public HomeTabsDataResponse getHomeTabsData(String apiName) {
        List<ProductInfo> allProducts = productInfoRepository.findAll();

        // Tính toán dựa trên stream/grouping, giả sử phân loại theo gender type
        HomePopularProductsDto men = calculatePopularProductsForGender(allProducts, "Men");
        HomePopularProductsDto women = calculatePopularProductsForGender(allProducts, "Women");
        HomePopularProductsDto boys = calculatePopularProductsForGender(allProducts, "Boys");
        HomePopularProductsDto girls = calculatePopularProductsForGender(allProducts, "Girls");
        HomePopularProductsDto essentials = calculatePopularProductsForCategory(allProducts, "Essentials");
        HomePopularProductsDto homeAndLiving = calculatePopularProductsForCategory(allProducts, "Home and Living");

        return new HomeTabsDataResponse(men, women, boys, girls, essentials, homeAndLiving);
    }
    
    public FilterAttributesResponse getProductFiltersByProducts(String queryParams) {
        ProductFilterRequest request = parseFilterRequestFromQueryParams(queryParams);

        Specification<ProductInfo> specification = buildSpecificationFromRequest(request);

        List<ProductInfo> products = productInfoRepository.findAll(specification);

        List<FilterAttributesWithTotalItemsDTO> genders = calculateFilterWithCounts(products, "genderCategory");
        List<FilterAttributesWithTotalItemsDTO> apparels = calculateFilterWithCounts(products, "apparelCategory");
        List<FilterAttributesWithTotalItemsDTO> brands = calculateFilterWithCounts(products, "productBrandCategory");
        List<FilterAttributesWithTotalItemsDTO> prices = calculatePriceRangesWithCounts(products);
        List<FilterAttributesWithTotalItemsDTO> sortOptions = getStaticSortOptions();

        return new FilterAttributesResponse(genders, apparels, brands, prices, sortOptions);
    }

    public SearchSuggestionResponse getSearchSuggestionList() {
        return new SearchSuggestionResponse(
                genderCategoryRepository.findAll(),
                productBrandCategoryRepository.findAll(),
                apparelCategoryRepository.findAll(),
                mapToTwoAttrDTO(productInfoRepository.findGenderAndApparelProjections()),
                mapToTwoAttrDTO(productInfoRepository.findGenderAndBrandProjections()),
                mapToTwoAttrDTO(productInfoRepository.findApparelAndBrandProjections()),
                mapToThreeAttrDTO(productInfoRepository.findGenderApparelBrandProjections()),
                productInfoRepository.findAllDistinctProductNames()
        );
    }

    private List<FilterAttributesWithTotalItemsDTO> calculateFilterWithCounts(List<ProductInfo> products, String categoryField) {
        return products.stream()
                .collect(Collectors.groupingBy(p -> getCategoryId(p, categoryField), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new FilterAttributesWithTotalItemsDTO(entry.getKey(), getCategoryName(entry.getKey(), categoryField), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Integer getCategoryId(ProductInfo product, String categoryField) {
        return switch (categoryField) {
            case "genderCategory" -> product.getGenderCategory().getId();
            case "apparelCategory" -> product.getApparelCategory().getId();
            case "productBrandCategory" -> product.getProductBrandCategory().getId();
            default -> 0;
        };
    }

    private String getCategoryName(Integer categoryId, String categoryField) {
        return switch (categoryField) {
            case "genderCategory" -> genderCategoryRepository.findById(categoryId).map(category -> category.getType()).orElse("");
            case "apparelCategory" -> apparelCategoryRepository.findById(categoryId).map(category -> category.getType()).orElse("");
            case "productBrandCategory" -> productBrandCategoryRepository.findById(categoryId).map(category -> category.getType()).orElse("");
            default -> "";
        };
    }

    private List<FilterAttributesWithTotalItemsDTO> calculatePriceRangesWithCounts(List<ProductInfo> products) {
        return products.stream()
                .collect(Collectors.groupingBy(this::getPriceRangeId, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new FilterAttributesWithTotalItemsDTO(entry.getKey(), getPriceRangeName(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Integer getPriceRangeId(ProductInfo product) {
        return 1; // Triển khai logic bin price vào range id dựa trên price-range-data.txt
    }

    private String getPriceRangeName(Integer rangeId) {
        return ""; // Triển khai từ DB hoặc static list dựa trên price-range-data.txt
    }

    private List<FilterAttributesWithTotalItemsDTO> getStaticSortOptions() {
        return List.of(
                new FilterAttributesWithTotalItemsDTO(1, "What's New", 0L),
                new FilterAttributesWithTotalItemsDTO(2, "Popularity", 0L),
                new FilterAttributesWithTotalItemsDTO(3, "Price: Low To High", 0L),
                new FilterAttributesWithTotalItemsDTO(4, "Price: High To Low", 0L)
        );
    }

    private HomePopularProductsDto calculatePopularProductsForGender(List<ProductInfo> products, String genderType) {
        List<ProductInfo> filtered = products.stream()
                .filter(p -> p.getGenderCategory().getType().equals(genderType))
                .toList();

        // Tính popular brands/apparels, ví dụ group by + count
        List<PopularProductDto> popularBrands = filtered.stream()
                .collect(Collectors.groupingBy(p -> p.getProductBrandCategory().getId(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new PopularProductDto(entry.getKey(), getBrandName(entry.getKey()), entry.getValue()))
                .sorted((a, b) -> b.getProductCount().compareTo(a.getProductCount()))
                .toList();

        List<PopularProductDto> popularApparels = filtered.stream()
                .collect(Collectors.groupingBy(p -> p.getApparelCategory().getId(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new PopularProductDto(entry.getKey(), getApparelName(entry.getKey()), entry.getValue()))
                .sorted((a, b) -> b.getProductCount().compareTo(a.getProductCount()))
                .toList();

        return new HomePopularProductsDto(popularBrands, popularApparels);
    }

    private HomePopularProductsDto calculatePopularProductsForCategory(List<ProductInfo> products, String categoryType) {
        // Tương tự, filter theo category nếu có field, hoặc logic business
        return new HomePopularProductsDto(List.of(), List.of()); // Triển khai tương tự
    }

    private String getBrandName(Integer brandId) {
        return productBrandCategoryRepository.findById(brandId).map(category -> category.getType()).orElse("");
    }

    private String getApparelName(Integer apparelId) {
        return apparelCategoryRepository.findById(apparelId).map(category -> category.getType()).orElse("");
    }

    private List<SearchSuggestionForTwoAttrDTO> mapToTwoAttrDTO(List<SearchSuggestionForTwoAttrProjection> projections) {
        return projections.stream()
                .map(projection -> new SearchSuggestionForTwoAttrDTO(
                        projection.getFirstId(),
                        projection.getFirstName(),
                        projection.getSecondId(),
                        projection.getSecondName()))
                .collect(Collectors.toList());
    }

    private List<SearchSuggestionForThreeAttrDTO> mapToThreeAttrDTO(List<SearchSuggestionForThreeAttrProjection> projections) {
        return projections.stream()
                .map(projection -> new SearchSuggestionForThreeAttrDTO(
                        projection.getFirstId(),
                        projection.getFirstName(),
                        projection.getSecondId(),
                        projection.getSecondName(),
                        projection.getThirdId(),
                        projection.getThirdName()))
                .collect(Collectors.toList());
    }

    

    private List<Integer> parseIds(String value) {
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<BigDecimal> parsePriceRanges(String value) {
        return new ArrayList<>(); // Triển khai parse min-max pairs, e.g., "0-50,50-100" -> list BigDecimal
    }

    private ProductFilterRequest.SortOption parseSortOption(String value) {
        return switch (value) {
            case "1" -> ProductFilterRequest.SortOption.NEWEST;
            case "2" -> ProductFilterRequest.SortOption.POPULARITY;
            case "3" -> ProductFilterRequest.SortOption.PRICE_LOW_TO_HIGH;
            case "4" -> ProductFilterRequest.SortOption.PRICE_HIGH_TO_HIGH;
            default -> null;
        };
    }

    private void parsePageInfo(String value, ProductFilterRequest request) {
        String[] pageInfo = value.split(",");
        if (pageInfo.length == 2) {
            request.setPageNumber(Integer.parseInt(pageInfo[0].trim()));
            request.setPageSize(Integer.parseInt(pageInfo[1].trim()));
        }
    }
}