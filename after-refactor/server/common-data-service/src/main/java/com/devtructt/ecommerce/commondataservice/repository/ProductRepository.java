package com.devtructt.ecommerce.commondataservice.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devtructt.ecommerce.commondataservice.dto.ProductFilterDto;
import com.devtructt.ecommerce.commondataservice.dto.ThreeProductFiltersDto;
import com.devtructt.ecommerce.commondataservice.dto.TwoProductFiltersDto;
import com.devtructt.ecommerce.commondataservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	@Query("""
	    SELECT DISTINCT p.name
	    FROM Product p
	    WHERE p.verificationStatus = true
		""")
	List<String> findDistinctProductNames();
	
    @Query("""
		SELECT NEW com.devtructt.ecommerce.commondataservice.dto.ProductFilterDto
    		(
	        a.id,
	        a.name,
	        COUNT(p.id)
      		)
        FROM Product p
        JOIN p.apparel a
        WHERE p.gender.id = :genderId 
          AND p.verificationStatus = true
        GROUP BY a.id, a.name
        ORDER BY COUNT(p.id) DESC
        """)
    List<ProductFilterDto> findApparelsByGender(@Param("genderId") Long genderId, Pageable pageable);
	
	@Query("""
		SELECT NEW com.devtructt.ecommerce.commondataservice.dto.ProductFilterDto
		(
	        b.id,
	        b.name,
	        COUNT(p.id)
        )
        FROM Product p
        JOIN p.brand b
        WHERE p.gender.id = :genderId 
          AND p.verificationStatus = true
        GROUP BY b.id, b.name
        ORDER BY COUNT(p.id) DESC
        """)
    List<ProductFilterDto> findBrandsByGender(@Param("genderId") Long genderId, Pageable pageable);

    @Query("""
		SELECT NEW com.devtructt.ecommerce.commondataservice.dto.ProductFilterDto
    		(
		        g.id,
		        g.name,
		        COUNT(p.id)
	        )
        FROM Product p 
        JOIN p.gender g 
        WHERE p.verificationStatus = true
          AND (:apparelIds IS NULL OR p.apparel.id IN :apparelIds)
          AND (:brandIds IS NULL OR p.brand.id IN :brandIds)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%')))
        GROUP BY g.id, g.name
        ORDER BY COUNT(p.id) DESC
        """)
    List<ProductFilterDto> findGendersByOtherProductFilters(
            @Param("apparelIds") Collection<Long> apparelIds,
            @Param("brandIds") Collection<Long> brandIds,
            @Param("productName") String productName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("""
		SELECT NEW com.devtructt.ecommerce.commondataservice.dto.ProductFilterDto
    		(
		        a.id,
		        a.name,
		        COUNT(p.id)
	        )
        FROM Product p 
        JOIN p.apparel a 
        WHERE p.verificationStatus = true
          AND (:genderIds IS NULL OR p.gender.id IN :genderIds)
          AND (:brandIds IS NULL OR p.brand.id IN :brandIds)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%')))
        GROUP BY a.id, a.name
        ORDER BY COUNT(p.id) DESC
        """)
    List<ProductFilterDto> findApparelsByOtherProductFilters(
            @Param("genderIds") Collection<Long> genderIds,
            @Param("brandIds") Collection<Long> brandIds,
            @Param("productName") String productName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("""
		SELECT NEW com.devtructt.ecommerce.commondataservice.dto.ProductFilterDto
    		(
		        b.id,
		        b.name,
		        COUNT(p.id)
	        )
        FROM Product p 
        JOIN p.brand b 
        WHERE p.verificationStatus = true
          AND (:genderIds IS NULL OR p.gender.id IN :genderIds)
          AND (:apparelIds IS NULL OR p.apparel.id IN :apparelIds)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%')))
        GROUP BY b.id, b.name
        ORDER BY COUNT(p.id) DESC
        """)
    List<ProductFilterDto> findBrandsByByOtherProductFilters(
            @Param("genderIds") Collection<Long> genderIds,
            @Param("apparelIds") Collection<Long> apparelIds,
            @Param("productName") String productName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("""
		SELECT DISTINCT NEW com.devtructt.ecommerce.commondataservice.dto.TwoProductFiltersDto
    		(
		        g.id,
		        g.name,
		        a.id,
		        a.name
	        )
	    FROM Product p
	    JOIN p.gender g
	    JOIN p.apparel a
	    WHERE p.verificationStatus = true
    	""")
	List<TwoProductFiltersDto> findDistinctGenderApparelCombinations();
    
    @Query("""
		SELECT DISTINCT NEW com.devtructt.ecommerce.commondataservice.dto.TwoProductFiltersDto
    		(
		        g.id,
		        g.name,
		        b.id,
		        b.name
	        )
	    FROM Product p
	    JOIN p.gender g
	    JOIN p.brand b
	    WHERE p.verificationStatus = true
    	""")
	List<TwoProductFiltersDto> findDistinctGenderBrandCombinations();
    
    @Query("""
		SELECT DISTINCT NEW com.devtructt.ecommerce.commondataservice.dto.TwoProductFiltersDto
    		(
		        a.id,
		        a.name,
		        b.id,
		        b.name
	        )
	    FROM Product p
	    JOIN p.apparel a
	    JOIN p.brand b
	    WHERE p.verificationStatus = true
    	""")
	List<TwoProductFiltersDto> findDistinctApparelBrandCombinations();
    
    @Query("""
		SELECT DISTINCT NEW com.devtructt.ecommerce.commondataservice.dto.ThreeProductFiltersDto
    		(
		        g.id,
		        g.name,
		        a.id,
		        a.name,
		        b.id,
		        b.name
	        )
	    FROM Product p
	    JOIN p.gender g
	    JOIN p.apparel a
	    JOIN p.brand b
	    WHERE p.verificationStatus = true
    	""")
	List<ThreeProductFiltersDto> findDistinctGenderApparelBrandCombinations();
}
