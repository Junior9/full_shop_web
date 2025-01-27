package com.api.shop.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryDto {

    private Long id;
    private String name;
}
