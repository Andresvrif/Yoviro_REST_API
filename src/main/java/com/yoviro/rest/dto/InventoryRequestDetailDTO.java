package com.yoviro.rest.dto;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import java.math.BigDecimal;

public class InventoryRequestDetailDTO {
    private Long id;
    private InventoryRequestStatusEnum unitMeasure;
    private ProductDTO productDTO;
    private BigDecimal quantity;
    private InventoryRequestDTO inventoryRequestDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryRequestStatusEnum getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(InventoryRequestStatusEnum unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public InventoryRequestDTO getInventoryRequestDTO() {
        return inventoryRequestDTO;
    }

    public void setInventoryRequestDTO(InventoryRequestDTO inventoryRequestDTO) {
        this.inventoryRequestDTO = inventoryRequestDTO;
    }
}
