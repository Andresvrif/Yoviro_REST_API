package com.yoviro.rest.dto.search;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import java.time.LocalDate;

public class SearchInventoryRequestDTO {
    private String inventoryRequestNumber;
    private InventoryRequestStatusEnum status;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getInventoryRequestNumber() {
        return inventoryRequestNumber;
    }

    public void setInventoryRequestNumber(String inventoryRequestNumber) {
        this.inventoryRequestNumber = inventoryRequestNumber;
    }

    public InventoryRequestStatusEnum getStatus() {
        return status;
    }

    public void setStatus(InventoryRequestStatusEnum status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}