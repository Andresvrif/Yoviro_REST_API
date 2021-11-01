package com.yoviro.rest.dto;

import com.yoviro.rest.models.entity.Product;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PurchaseOrderDetailDTO {
    private ProductDTO product;

    @NotNull
    private BigDecimal quantity;

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
}
