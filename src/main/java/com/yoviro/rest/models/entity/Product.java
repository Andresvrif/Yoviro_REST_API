package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.ProductCategoryEnum;
import com.yoviro.rest.config.enums.UnitMeasureEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sku;

    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @NotNull
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private UnitMeasureEnum unitMeasure;

    @NotNull
    @Column(columnDefinition = "tinyint(1) default 1")
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

    public ProductCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(ProductCategoryEnum category) {
        this.category = category;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public UnitMeasureEnum getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasureEnum unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @PrePersist
    public void PrePersist() {
        this.createAt = LocalDateTime.now();
    }

    private static final long serialVersionUID = 1L;
}