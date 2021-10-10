package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class InventoryRequestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InventoryRequestStatusEnum unitMeasure;

    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_request_id")
    private InventoryRequest inventoryRequest;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public InventoryRequest getInventoryRequest() {
        return inventoryRequest;
    }

    public void setInventoryRequest(InventoryRequest inventoryRequest) {
        this.inventoryRequest = inventoryRequest;
    }

    private static final long serialVersionUID = 1L;
}
