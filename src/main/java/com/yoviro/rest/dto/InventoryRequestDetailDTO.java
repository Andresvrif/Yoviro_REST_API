package com.yoviro.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

public class InventoryRequestDetailDTO {
    @JsonIgnore
    private Long id;
    private ProductDTO product;
    private BigDecimal quantity;
    private InventoryRequestDTO inventoryRequestDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
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
