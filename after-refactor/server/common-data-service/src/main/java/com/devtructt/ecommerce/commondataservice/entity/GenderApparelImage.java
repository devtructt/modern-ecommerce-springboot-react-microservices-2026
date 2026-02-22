package com.devtructt.ecommerce.commondataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = {"gender", "apparel"})
@EqualsAndHashCode(exclude = {"gender", "apparel"})
@Entity
public class GenderApparelImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String localImagePath;

    private String imageUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Apparel apparel;

    public GenderApparelImage(String title, String localImagePath, String imageUrl) {
        this.title = title;
        this.localImagePath = localImagePath;
        this.imageUrl = imageUrl;
    }
}