package com.devtructt.ecommerce.searchsuggestionservice.dto;

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
public class TwoProductFiltersDto {
    Long firstProductFilterId;
    String firstProductFilterName;
    
    Long secondProductFilterId;
    String secondProductFilterName;
}