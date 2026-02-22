package com.devtructt.ecommerce.commondataservice.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devtructt.ecommerce.commondataservice.entity.Apparel;
import com.devtructt.ecommerce.commondataservice.entity.Brand;
import com.devtructt.ecommerce.commondataservice.entity.BrandImage;
import com.devtructt.ecommerce.commondataservice.entity.CarouselImage;
import com.devtructt.ecommerce.commondataservice.entity.Gender;
import com.devtructt.ecommerce.commondataservice.entity.GenderApparelImage;
import com.devtructt.ecommerce.commondataservice.entity.PriceRange;
import com.devtructt.ecommerce.commondataservice.entity.Product;
import com.devtructt.ecommerce.commondataservice.entity.SortOption;
import com.devtructt.ecommerce.commondataservice.enums.ImageType;
import com.devtructt.ecommerce.commondataservice.enums.ProductFilter;
import com.devtructt.ecommerce.commondataservice.repository.ApparelRepository;
import com.devtructt.ecommerce.commondataservice.repository.BrandImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.BrandRepository;
import com.devtructt.ecommerce.commondataservice.repository.CarouselImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.GenderApparelImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.GenderRepository;
import com.devtructt.ecommerce.commondataservice.repository.PriceRangeRepository;
import com.devtructt.ecommerce.commondataservice.repository.ProductRepository;
import com.devtructt.ecommerce.commondataservice.repository.SortOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoadSampleDataService {
	public final static String SAMPLE_DATA_DIRECTORY = "sample-data";
	public final static String PRODUCT_DATA_FILE_NAME = "product-data.txt";
	public final static String PRICE_RANGE_DATA_FILE_NAME = "price-range-data.txt";
	public final static String SORT_OPTION_DATA_FILE_NAME = "sort-option-data.txt";
	public final static String IMAGE_DATA_FILE_NAME = "image-data.txt";

	private final ProductRepository productRepository;

	private final ApparelRepository apparelRepository;
	private final GenderRepository genderRepository;
	private final BrandRepository brandRepository;

	private final PriceRangeRepository priceRangeRepository;
	private final SortOptionRepository sortOptionRepository;

	private final BrandImageRepository brandImageRepository;
	private final GenderApparelImageRepository genderApparelImageRepository;
	private final CarouselImageRepository carouselImageRepository;

	private final ResourceLoader resourceLoader;
	
	@Transactional
	public void loadSampleData() {
		loadProductFilterData(ProductFilter.SORT_OPTION, SORT_OPTION_DATA_FILE_NAME);
		loadProductFilterData(ProductFilter.PRICE_RANGE, PRICE_RANGE_DATA_FILE_NAME);
		loadProductData();
		loadImageData();
	}
	
	@Transactional
	public void loadProductFilterData(ProductFilter productFilterType, String fileName) {
		Resource resource = resourceLoader.getResource("classpath:" + SAMPLE_DATA_DIRECTORY + "/" + fileName);
		if (!resource.exists()) {
			throw new RuntimeException("Sample data file not found: " + fileName);
		}
		
		try {
			Path path = Paths.get(resource.getURI());
			Files.lines(path, StandardCharsets.UTF_8).forEach(line -> {
			    String[] parts = line.split("\\|");
			    String id = parts[0];
			    String name = parts[1];
			    
				switch (productFilterType) {
			        case SORT_OPTION -> {
			            if (sortOptionRepository.findByName(name).isEmpty()) {
			            	sortOptionRepository.save(new SortOption(Long.valueOf(id), name));
			            }
			        }
			        case PRICE_RANGE -> {
			        	BigDecimal minPrice = new BigDecimal(parts[2]);
			        	BigDecimal maxPrice = new BigDecimal(parts[3]);
			            if (priceRangeRepository.findByName(name).isEmpty()) {
			            	priceRangeRepository.save(new PriceRange(Long.valueOf(id), name, minPrice, maxPrice));
			            }
			        }
			        default -> throw new IllegalArgumentException("Unexpected value: " + productFilterType);
				}
			});
		} catch (IOException exception) {
			throw new RuntimeException("Failed to load product filter data", exception);
		}
	}
	
	@Transactional
	public void loadImageData() {
		Resource resource = resourceLoader.getResource("classpath:" + SAMPLE_DATA_DIRECTORY + "/" + IMAGE_DATA_FILE_NAME);
		if (!resource.exists()) {
			throw new RuntimeException("Sample data file not found: " + IMAGE_DATA_FILE_NAME);
		}

		try {
			Path path = Paths.get(resource.getURI());
			Files.lines(path, StandardCharsets.UTF_8).forEach(line -> {
				String[] parts = line.split("\\|");
				ImageType imageType = ImageType.valueOf(parts[0].toUpperCase());
				String localImagePath = parts[1];
				String imageUrl = parts[2];
				String primaryCategory = parts[3];
				String secondaryCategory = parts.length > 4 ? parts[4] : null;
				
				switch (imageType) {
					case BRAND -> {
						Optional<Brand> brand = brandRepository.findByName(primaryCategory);
						String title = primaryCategory;
						if (brand.isPresent()) {
							BrandImage brandImage = new BrandImage(title, localImagePath, imageUrl);
							brandImage.setBrand(brand.get());
							brandImageRepository.save(brandImage);
						}
					}
					case GENDER_APPAREL -> {
						Optional<Apparel> apparel = apparelRepository.findByName(primaryCategory);
						Optional<Gender> gender = genderRepository.findByName(secondaryCategory);
						String title = primaryCategory + " - " + secondaryCategory;
						if (apparel.isPresent() && gender.isPresent()) {
							GenderApparelImage genderApparelImage = new GenderApparelImage(title + secondaryCategory, localImagePath, imageUrl);
							genderApparelImage.setApparel(apparel.get());
							genderApparelImage.setGender(gender.get());
							genderApparelImageRepository.save(genderApparelImage);
						}
					}
					case CAROUSEL -> {
						List<String> genderIds = Arrays.stream(primaryCategory.split(","))
									.map(String::trim)
									.map(genderRepository::findByName)
									.flatMap(Optional::stream)
									.map(Gender::getId)
									.map(String::valueOf)
									.toList();
						String title = "Carousel - " + primaryCategory;
						String link = genderIds.isEmpty() ? null : ProductFilter.GENDER.getQueryParam() + String.join(",", genderIds);
						CarouselImage carouselImage = new CarouselImage(title, localImagePath, imageUrl, link);
						carouselImageRepository.save(carouselImage);
					}
					default -> throw new IllegalArgumentException("Unexpected value: " + imageType);
				}
			});
		} catch (IOException exception) {
			throw new RuntimeException("Failed to load image data", exception);
		}
	}
	
	@Transactional
	public void loadProductData() {
		LocalDate startDate = LocalDate.of(2025, 1, 1);
		LocalDate endDate = LocalDate.of(2025, 12, 31);
		
		Resource resource = resourceLoader.getResource("classpath:" + SAMPLE_DATA_DIRECTORY + "/" + PRODUCT_DATA_FILE_NAME);
		if (!resource.exists()) {
			throw new RuntimeException("Sample data file not found: " + PRODUCT_DATA_FILE_NAME);
		}
		
		try {
			Path path = Paths.get(resource.getURI());
			Files.lines(path, StandardCharsets.UTF_8).forEach(line -> {
				String[] lineParts = line.split("\\|");
				String genderName = lineParts[0];
				String apparelName = lineParts[1];
				String brandName = lineParts[2];
				String productName = lineParts[3];
				BigDecimal price = new BigDecimal(lineParts[4]);
				String fileName = lineParts[5];
				String imageUrl = lineParts[6];
				String localImagePath = normalizeFilePath(genderName + "/" + apparelName + "/" + fileName);
				
				Gender gender = genderRepository.findByName(genderName).orElseGet(() -> genderRepository.save(new Gender(genderName)));
				Apparel apparel = apparelRepository.findByName(apparelName).orElseGet(() -> apparelRepository.save(new Apparel(apparelName)));
				Brand brand = brandRepository.findByName(brandName).orElseGet(() -> brandRepository.save(new Brand(brandName)));
				PriceRange priceRange = findPriceRangeByPrice(price).get();
				
				Product product = Product.builder()
				        .sellerId(1L)
				        .name(productName)
				        .publicationDate(randomDateBetween(startDate, endDate))
				        .gender(gender)
				        .apparel(apparel)
				        .brand(brand)
				        .priceRange(priceRange)
				        .price(price)
				        .availableQuantity(randomIntBetween(1, 100 + 1))
				        .deliveryTime(randomIntBetween(2, 5 + 1))
				        .rating(randomDecimalBetween(BigDecimal.ZERO, BigDecimal.valueOf(5), 1))
				        .verificationStatus(true)
				        .localImagePath(localImagePath)
				        .imageUrl(imageUrl)
				        .build();
				productRepository.save(product);
			});
		} catch (IOException exception) {
			throw new RuntimeException("Failed to load product data", exception);
		}
	}

	private Optional<PriceRange> findPriceRangeByPrice(BigDecimal price) {
		return priceRangeRepository.findAll().stream()
				.filter(range -> range.getMinPrice().compareTo(price) <= 0 && range.getMaxPrice().compareTo(price) >= 0)
				.findFirst();
	}

	private String normalizeFilePath(String path) {
		return path.replaceAll("\\s", "_");
	}

	private int randomIntBetween(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max);
	}

	private BigDecimal randomDecimalBetween(BigDecimal min, BigDecimal max, int scale) {
		BigDecimal randomDecimal = min.add(max.subtract(min).multiply(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble())));
		return randomDecimal.setScale(scale, RoundingMode.HALF_UP);
	}

	private LocalDate randomDateBetween(LocalDate start, LocalDate end) {
	    long randomDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay());
	    return LocalDate.ofEpochDay(randomDay);
	}
}