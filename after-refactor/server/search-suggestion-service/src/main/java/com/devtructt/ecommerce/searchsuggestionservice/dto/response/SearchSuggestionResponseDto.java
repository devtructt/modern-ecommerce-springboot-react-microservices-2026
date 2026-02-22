package com.devtructt.ecommerce.searchsuggestionservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class SearchSuggestionResponseDto {
    @JsonProperty("genderKeywords")
    List<AttributeKeywordDto> genderKeywords;

    @JsonProperty("brandKeywords")
    List<AttributeKeywordDto> brandKeywords;

    @JsonProperty("apparelKeywords")
    List<AttributeKeywordDto> apparelKeywords;

    @JsonProperty("productKeywords")
    List<String> productKeywords;

    @JsonProperty("genderApparelKeywords")
    List<CombinationKeywordDto> genderApparelKeywords;

    @JsonProperty("genderBrandKeywords")
    List<CombinationKeywordDto> genderBrandKeywords;

    @JsonProperty("apparelBrandKeywords")
    List<CombinationKeywordDto> apparelBrandKeywords;

    @JsonProperty("threeAttrKeywords")
    List<ThreeAttributeKeywordDto> threeAttributeKeywords;

    @Value
    public static class AttributeKeywordDto {
        String type;

        @JsonProperty("id")
        String attributeId;
    }

    @Value
    public static class CombinationKeywordDto {
        @JsonProperty("attr1_type")
        String firstAttributeType;

        @JsonProperty("attr1_id")
        String firstAttributeId;

        @JsonProperty("attr2_type")
        String secondAttributeType;

        @JsonProperty("attr2_id")
        String secondAttributeId;
    }

    @Value
    public static class ThreeAttributeKeywordDto {
        @JsonProperty("attr1_type")
        String firstAttributeType;

        @JsonProperty("attr1_id")
        String firstAttributeId;

        @JsonProperty("attr2_type")
        String secondAttributeType;

        @JsonProperty("attr2_id")
        String secondAttributeId;

        @JsonProperty("attr3_type")
        String thirdAttributeType;

        @JsonProperty("attr3_id")
        String thirdAttributeId;
    }
}