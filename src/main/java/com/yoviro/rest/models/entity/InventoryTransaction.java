package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.CurrencyEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "InventoryTransactionType", discriminatorType = DiscriminatorType.STRING)
public abstract class InventoryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal unitValue; //NO CONSIDERAR

    private LocalDateTime createAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_balance_id")
    private InventoryBalance balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(BigDecimal unitValue) {
        this.unitValue = unitValue;
    }

    public InventoryBalance getBalance() {
        return balance;
    }

    public void setBalance(InventoryBalance balance) {
        this.balance = balance;
    }


    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    protected static final long serialVersionUID = 1L;
}