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
import com.devtructt.ecommerce.commondataservice.entity.Product;
import com.devtructt.ecommerce.commondataservice.enums.ImageType;
import com.devtructt.ecommerce.commondataservice.enums.ProductFilter;
import com.devtructt.ecommerce.commondataservice.repository.ApparelRepository;
import com.devtructt.ecommerce.commondataservice.repository.BrandImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.BrandRepository;
import com.devtructt.ecommerce.commondataservice.repository.CarouselImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.GenderApparelImageRepository;
import com.devtructt.ecommerce.commondataservice.repository.GenderRepository;
import com.devtructt.ecommerce.commondataservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoadSampleDataService {
	public final static String SAMPLE_DATA_DIRECTORY = "sample-data";
	
	public final static String PRODUCT_DATA_FILE_NAME = "product-data.txt";
	public final static String IMAGE_DATA_FILE_NAME = "image-data.txt";

	private final ProductRepository productRepository;

	private final ApparelRepository apparelRepository;
	private final GenderRepository genderRepository;
	private final BrandRepository brandRepository;

	private final BrandImageRepository brandImageRepository;
	private final GenderApparelImageRepository genderApparelImageRepository;
	private final CarouselImageRepository carouselImageRepository;

	private final ResourceLoader resourceLoader;
	
	@Transactional
	public void loadSampleData() {
		loadProductData();
		loadImageData();
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
				String imageName = lineParts[5];
				String imageUrl = lineParts[6];
				String localImagePath = normalizeFilePath(genderName + "/" + apparelName + "/" + brandName + "/" + imageName);
				
				Gender gender = genderRepository.findByName(genderName).orElseGet(() -> genderRepository.save(new Gender(genderName)));
				Apparel apparel = apparelRepository.findByName(apparelName).orElseGet(() -> apparelRepository.save(new Apparel(apparelName)));
				Brand brand = brandRepository.findByName(brandName).orElseGet(() -> brandRepository.save(new Brand(brandName)));
				
				Product product = Product.builder()
				        .sellerId(1L)
				        .name(productName)
				        .publicationDate(randomDateBetween(startDate, endDate))
				        .gender(gender)
				        .apparel(apparel)
				        .brand(brand)
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
							GenderApparelImage genderApparelImage = new GenderApparelImage(title, localImagePath, imageUrl);
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