package com.yoviro.rest.dto.search;

public class SearchAgreementDTO {
    private SearchJobDTO searchJobDTO;
    private String agreementNumber;

    public SearchJobDTO getSearchJobDTO() {
        return searchJobDTO;
    }

    public void setSearchJobDTO(SearchJobDTO searchJobDTO) {
        this.searchJobDTO = searchJobDTO;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
}
