package com.devtructt.ecommerce.searchsuggestionservice.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class PermutationGenerator {
    private List<String> permutations = new ArrayList<>();;

    public PermutationGenerator(String[] inputArray) {
        if (inputArray.length > 0) {
            permute(inputArray, 0, inputArray.length - 1);
        }
    }

    private void permute(String[] inputArray, int left, int right) {
        if (left == right) {
            permutations.add(String.join(" ", inputArray));
            return;
        }

        for (int index = left; index <= right; index++) {
            swap(inputArray, left, index);
            permute(inputArray, left + 1, right);
            swap(inputArray, left, index);
        }
    }

    private void swap(String[] array, int firstIndex, int secondIndex) {
        String temporary = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temporary;
    }
}