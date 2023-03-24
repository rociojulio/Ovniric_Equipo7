package com.ovniric.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
public class ProductDTO {

    private Long id;
    private String title;
    private Long categoryId;
    private Long locationId;
    private Integer altitude;
    private Set<String> imageUrl;
    private Set<String> featureTitle;
    private String description;
    private Integer maxReservations;
    private String policy;


}
