package com.yoviro.rest.dto.search;

public class SearchResidentDTO {
    SearchPersonDTO searchPersonDTO;
    private Boolean enable;

    public SearchPersonDTO getSearchPersonDTO() {
        return searchPersonDTO;
    }

    public void setSearchPersonDTO(SearchPersonDTO searchPersonDTO) {
        this.searchPersonDTO = searchPersonDTO;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}