package com.yoviro.rest.models.repository.specification.handler;

import java.util.ArrayList;
import java.util.List;

public class JoinColumnProps {
    private String joinColumnName;
    private List<SearchFilter> searchFilter;
    private JoinColumnProps subJoinColumnProps;

    public String getJoinColumnName() {
        return joinColumnName;
    }

    public void setJoinColumnName(String joinColumnName) {
        this.joinColumnName = joinColumnName;
    }

    public List<SearchFilter> getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(List<SearchFilter> searchFilter) {
        this.searchFilter = searchFilter;
    }

    public JoinColumnProps getSubJoinColumnProps() {
        return subJoinColumnProps;
    }

    public void setSubJoinColumnProps(JoinColumnProps subJoinColumnProps) {
        this.subJoinColumnProps = subJoinColumnProps;
    }
}