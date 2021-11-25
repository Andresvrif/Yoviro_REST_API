package com.yoviro.rest.dto.search;

import com.yoviro.rest.config.enums.PurcharseOrderEnum;
import org.springframework.stereotype.Component;

public class SearchPurchaseOrderDTO {
    SearchProposalDTO proposalCriteria;
    PurcharseOrderEnum status;
    String purchaseOrderNumber;

    public SearchProposalDTO getProposalCriteria() {
        return proposalCriteria;
    }

    public void setProposalCriteria(SearchProposalDTO proposalCriteria) {
        this.proposalCriteria = proposalCriteria;
    }

    public PurcharseOrderEnum getStatus() {
        return status;
    }

    public void setStatus(PurcharseOrderEnum status) {
        this.status = status;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }
}