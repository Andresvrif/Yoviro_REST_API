package com.yoviro.rest.dto.search;

import java.time.LocalDate;

public class SearchCompanyDTO extends SearchContactDTO {
    private LocalDate startDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
