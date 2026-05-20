package com.devtructt.ecommerce.searchsuggestionservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PermutationGeneratorUtil {
    public List<String> generate(String[] inputArray) {
    	List<String> permutations = new ArrayList<>();
        if (inputArray.length > 0) {
            permute(inputArray, 0, inputArray.length - 1, permutations);
        }
        return permutations;
    }

    private void permute(String[] inputArray, int left, int right, List<String> permutations) {
        if (left == right) {
            permutations.add(String.join(" ", inputArray));
            return;
        }

        for (int index = left; index <= right; index++) {
            swap(inputArray, left, index);
            permute(inputArray, left + 1, right, permutations);
            swap(inputArray, left, index);
        }
    }

    private void swap(String[] array, int firstIndex, int secondIndex) {
        String temporary = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temporary;
    }
}