package com.yoviro.rest.models.repository.specification.handler;

import java.util.ArrayList;
import java.util.List;

public class SearchQuery {
    private List<SearchFilter> searchFilter;

    private int pageNumber;
    private int pageSize;

    private SortOrder sortOrder;

    private List<JoinColumnProps> joinColumnProps;

    public List<SearchFilter> getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(List<SearchFilter> searchFitler) {
        this.searchFilter = searchFitler;
    }

    public void addSearchFilter(SearchFilter searchFilter){
        if (this.searchFilter == null){
            this.searchFilter = new ArrayList<SearchFilter>();
        }
        this.searchFilter.add(searchFilter);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<JoinColumnProps> getJoinColumnProps() {
        return joinColumnProps;
    }

    public void setJoinColumnProps(List<JoinColumnProps> joinColumnProps) {
        this.joinColumnProps = joinColumnProps;
    }

    public void addJoinColumnProp(JoinColumnProps joinColumnProps){
        if (this.joinColumnProps == null){
            this.joinColumnProps = new ArrayList<JoinColumnProps>();
        }
        this.joinColumnProps.add(joinColumnProps);
    }
}
