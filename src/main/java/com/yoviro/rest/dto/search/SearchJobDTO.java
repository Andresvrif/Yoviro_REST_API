package com.yoviro.rest.dto.search;

public class SearchJobDTO {
    SearchResidentDTO searchResidentDTO;
    String jobNumber;

    public SearchResidentDTO getSearchResidentDTO() {
        return searchResidentDTO;
    }

    public void setSearchResidentDTO(SearchResidentDTO searchResidentDTO) {
        this.searchResidentDTO = searchResidentDTO;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
}
