package com.yoviro.rest.dto.search;

public class SearchResidentDTO {
    SearchContactDTO searchContactDTO;
    private Boolean enable;

    public SearchContactDTO getSearchContactDTO() {
        return searchContactDTO;
    }

    public void setSearchContactDTO(SearchContactDTO searchContactDTO) {
        this.searchContactDTO = searchContactDTO;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}