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
public class ThreeProductFiltersDto {
    Long firstProductFilterId;
    String firstProductFilterName;
    
    Long secondProductFilterId;
    String secondProductFilterName;
    
    Long thirdProductFilterId;
    String thirdProductFilterName;
}