package com.yoviro.rest.dto;

import com.yoviro.rest.config.enums.PurcharseOrderEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDTO {
    private Long id;
    private String purchaseOrderNumber;
    private String referenceNumber; //Reference Number, número de referencia por parte del proveedor
    private String reasonForDenied;
    private BigDecimal totalPrice;
    private CompanyDTO company;
    private PurcharseOrderEnum status;
    private List<PurchaseOrderDetailDTO> purchaseOrderDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public List<PurchaseOrderDetailDTO> getPurchaseOrderDetails() {
        return purchaseOrderDetails;
    }

    public void setPurchaseOrderDetails(List<PurchaseOrderDetailDTO> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReasonForDenied() {
        return reasonForDenied;
    }

    public void setReasonForDenied(String reasonForDenied) {
        this.reasonForDenied = reasonForDenied;
    }

    public PurcharseOrderEnum getStatus() {
        return status;
    }

    public void setStatus(PurcharseOrderEnum status) {
        this.status = status;
    }
}
