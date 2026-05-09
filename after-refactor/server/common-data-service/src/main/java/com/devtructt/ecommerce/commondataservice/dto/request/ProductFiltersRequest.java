package com.devtructt.ecommerce.commondataservice.dto.request;

import java.math.BigDecimal;
import java.util.List;

import com.devtructt.ecommerce.commondataservice.enums.SortOption;

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
public class ProductFiltersRequest {
	private List<Long> genderIds;
    private List<Long> apparelIds;
    private List<Long> brandIds;
    
    private String productName;
    
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    
    private String keyword;
    
    private Integer pageNumber;
    private Integer pageSize;
    
    private SortOption sortOption;
}