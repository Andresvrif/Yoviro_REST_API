package com.yoviro.rest.dto;

import com.yoviro.rest.config.enums.ProductCategoryEnum;
import com.yoviro.rest.config.enums.UnitMeasureEnum;

import java.time.LocalDateTime;

public class ProductDTO {
    private Long id;
    private String sku;
    private ProductCategoryEnum category;
    private UnitMeasureEnum unitMeasure;
    private String name;
    private String description;
    private LocalDateTime createAt;
    private Boolean enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public ProductCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(ProductCategoryEnum category) {
        this.category = category;
    }

    public UnitMeasureEnum getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasureEnum unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}