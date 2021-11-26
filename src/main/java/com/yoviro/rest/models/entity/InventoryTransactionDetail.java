package com.yoviro.rest.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class InventoryTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_transaction_id")
    private InventoryTransaction inventoryTransaction;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "inventory_balance_id")
    private InventoryBalance balance;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    private BigDecimal quantity;

    private BigDecimal unitValue; //TODO - NO CONSIDERAR - PARA FUTURAS IMPLEMENTACIONES

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryTransaction getInventoryTransaction() {
        return inventoryTransaction;
    }

    public void setInventoryTransaction(InventoryTransaction inventoryTransaction) {
        this.inventoryTransaction = inventoryTransaction;
    }

    public InventoryBalance getBalance() {
        return balance;
    }

    public void setBalance(InventoryBalance balance) {
        this.balance = balance;
    }

    public InventoryTransaction getTransaction() {
        return inventoryTransaction;
    }

    public void setTransaction(InventoryTransaction transaction) {
        this.inventoryTransaction = transaction;
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

    public BigDecimal getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(BigDecimal unitValue) {
        this.unitValue = unitValue;
    }
}
