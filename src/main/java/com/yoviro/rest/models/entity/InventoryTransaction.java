package com.yoviro.rest.models.entity;

import com.yoviro.rest.config.enums.CurrencyEnum;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "InventoryTransactionType", discriminatorType = DiscriminatorType.STRING)
public abstract class InventoryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "inventoryTransaction",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.REMOVE
            },
            orphanRemoval = true)
    private List<InventoryTransactionDetail> details = new ArrayList<>();

    private LocalDateTime createAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public List<InventoryTransactionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InventoryTransactionDetail> details) {
        this.details = details;
    }

    protected static final long serialVersionUID = 1L;
}