package com.devtructt.ecommerce.commondataservice.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devtructt.ecommerce.commondataservice.dto.ThreeProductFiltersDto;
import com.devtructt.ecommerce.commondataservice.dto.TwoProductFiltersDto;
import com.devtructt.ecommerce.commondataservice.dto.projection.ProductFilterProjection;
import com.devtructt.ecommerce.commondataservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	@Query("""
	    SELECT DISTINCT p.name
	    FROM Product p
	    WHERE p.verificationStatus = true
		""")
	List<String> findDistinctProductNames();
	
    @Query("""
        SELECT a.id AS id, 
               a.name AS name, 
               COUNT(p.id) AS productCount
        FROM Product p
        JOIN p.apparel a
        WHERE p.gender.id = :genderId 
          AND p.verificationStatus = true
        GROUP BY a.id, a.name
        ORDER BY productCount DESC
        """)
    List<ProductFilterProjection> findApparelsByGender(@Param("genderId") Long genderId, Pageable pageable);
	
	@Query("""
	        SELECT b.id AS id, 
	               b.name AS name, 
	               COUNT(p.id) AS productCount
	        FROM Product p
	        JOIN p.brand b
	        WHERE p.gender.id = :genderId 
	          AND p.verificationStatus = true
	        GROUP BY b.id, b.name
	        ORDER BY productCount DESC
	        """)
	    List<ProductFilterProjection> findBrandsByGender(@Param("genderId") Long genderId, Pageable pageable);

    @Query("""
        SELECT g.id AS id,
    		   g.name AS name,
    		   COUNT(p.id) AS productCount
        FROM Product p 
        JOIN p.gender g 
        WHERE p.verificationStatus = true
          AND (:apparelIds IS NULL OR p.apparel.id IN :apparelIds)
          AND (:brandIds IS NULL OR p.brand.id IN :brandIds)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%')))
        GROUP BY g.id, g.name
        ORDER BY productCount DESC
        """)
    List<ProductFilterProjection> findGendersByOtherProductFilters(
            @Param("apparelIds") Collection<Long> apparelIds,
            @Param("brandIds") Collection<Long> brandIds,
            @Param("productName") String productName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("""
        SELECT a.id AS id,
    		   a.name AS name,
    		   COUNT(p.id) AS productCount
        FROM Product p 
        JOIN p.apparel a 
        WHERE p.verificationStatus = true
          AND (:genderIds IS NULL OR p.gender.id IN :genderIds)
          AND (:brandIds IS NULL OR p.brand.id IN :brandIds)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%')))
        GROUP BY a.id, a.name
        ORDER BY productCount DESC
        """)
    List<ProductFilterProjection> findApparelsByOtherProductFilters(
            @Param("genderIds") Collection<Long> genderIds,
            @Param("brandIds") Collection<Long> brandIds,
            @Param("productName") String productName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("""
        SELECT b.id AS id,
    		   b.name AS name,
    		   COUNT(p.id) AS productCount
        FROM Product p 
        JOIN p.brand b 
        WHERE p.verificationStatus = true
          AND (:genderIds IS NULL OR p.gender.id IN :genderIds)
          AND (:apparelIds IS NULL OR p.apparel.id IN :apparelIds)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%')))
        GROUP BY b.id, b.name
        ORDER BY productCount DESC
        """)
    List<ProductFilterProjection> findBrandsByByOtherProductFilters(
            @Param("genderIds") Collection<Long> genderIds,
            @Param("apparelIds") Collection<Long> apparelIds,
            @Param("productName") String productName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("""
    	SELECT DISTINCT g.id AS firstProductFilterId,
    			        g.name AS firstProductFilterName,
    			        a.id AS secondProductFilterId,
    			        a.name AS secondProductFilterName
	    FROM Product p
	    WHERE p.verificationStatus = true
	    JOIN p.gender g
	    JOIN p.apparel a
    	""")
	List<TwoProductFiltersDto> findDistinctGenderApparelSuggestions();
    
    @Query("""
    	SELECT DISTINCT g.id AS firstProductFilterId,
    			        g.name AS firstProductFilterName,
    			        b.id AS secondProductFilterId,
    			        b.name AS secondProductFilterName
	    FROM Product p
	    WHERE p.verificationStatus = true
	    JOIN p.gender g
	    JOIN p.brand b
    	""")
	List<TwoProductFiltersDto> findDistinctGenderBrandSuggestions();
    
    @Query("""
    	SELECT DISTINCT a.id AS firstProductFilterId,
    			        a.name AS firstProductFilterName,
    			        b.id AS secondProductFilterId,
    			        b.name AS secondProductFilterName
	    FROM Product p
	    WHERE p.verificationStatus = true
	    JOIN p.apparel a
	    JOIN p.brand b
    	""")
	List<TwoProductFiltersDto> findDistinctApparelBrandSuggestions();
    
    @Query("""
    	SELECT DISTINCT g.id AS firstProductFilterId,
    			        g.name AS firstProductFilterName,
    			        a.id AS secondProductFilterId,
    			        a.name AS secondProductFilterName,
    			        b.id AS thirdProductFilterId,
    			        b.name AS thirdProductFilterName
	    FROM Product p
	    WHERE p.verificationStatus = true
	    JOIN p.gender g
	    JOIN p.apparel a
	    JOIN p.brand b
    	""")
	List<ThreeProductFiltersDto> findDistinctGenderApparelBrandSuggestions();
}
