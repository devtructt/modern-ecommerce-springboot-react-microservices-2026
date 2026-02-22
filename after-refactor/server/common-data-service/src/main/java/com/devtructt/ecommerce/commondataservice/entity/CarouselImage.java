package com.devtructt.ecommerce.commondataservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class CarouselImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;

    private String localImagePath;

    private String imageUrl;
    
    private String link;
    
    public CarouselImage(String title, String localImagePath, String imageUrl, String link) {
        this.title = title;
        this.localImagePath = localImagePath;
        this.imageUrl = imageUrl;
        this.link = link;
    }
}