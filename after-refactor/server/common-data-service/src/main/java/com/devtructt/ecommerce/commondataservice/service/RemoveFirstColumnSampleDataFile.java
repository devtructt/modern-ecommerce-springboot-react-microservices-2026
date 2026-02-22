package com.devtructt.ecommerce.commondataservice.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class RemoveFirstColumnSampleDataFile {
    public static void main(String[] args) {
        Path filePath = Path.of("product-data.txt");

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String line;
            while ((line = reader.readLine()) != null) {

                int firstDelimiterIndex = line.indexOf('|');
                if (firstDelimiterIndex == -1) {
                    continue;
                }

                String withoutId = line.substring(firstDelimiterIndex + 1);
                System.out.println(withoutId);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
