package com.yoviro.rest.service;

import com.yoviro.rest.dto.search.SearchProductDTO;
import com.yoviro.rest.dto.search.SearchProposalDTO;
import com.yoviro.rest.models.entity.Product;
import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.models.repository.ProposalRepository;
import com.yoviro.rest.models.repository.specification.handler.OperatorEnum;
import com.yoviro.rest.models.repository.specification.handler.SearchFilter;
import com.yoviro.rest.models.repository.specification.handler.SearchQuery;
import com.yoviro.rest.models.repository.specification.handler.SpecificationUtil;
import com.yoviro.rest.service.interfaces.IProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProposalServiceImpl implements IProposalService {

    @Autowired
    ProposalRepository proposalRepository;

    @Override
    public Page<Proposal> search(Pageable pageable,
                                 SearchProposalDTO criteria) {
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> productCriteria = instanceProposalCriteria(criteria); //Proposal Filter
        qry.setSearchFilter(productCriteria);

        Specification<Proposal> specification = SpecificationUtil.bySearchQuery(qry, Proposal.class, Boolean.FALSE);
        return proposalRepository.findAll(specification, pageable);
    }

    static List<SearchFilter> instanceProposalCriteria(SearchProposalDTO criteria) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter proposalFilter;
        if (criteria.getProposalNumber() != null) {
            proposalFilter = new SearchFilter();
            proposalFilter.setProperty("proposalNumber");
            proposalFilter.setValue(criteria.getProposalNumber());
            proposalFilter.setOperator(OperatorEnum.LIKE);

            filters.add(proposalFilter);
        }

        if (criteria.getStatus() != null) {
            proposalFilter = new SearchFilter();
            proposalFilter.setProperty("status");
            proposalFilter.setValue(criteria.getStatus());
            proposalFilter.setOperator(OperatorEnum.EQUALS);

            filters.add(proposalFilter);
        }

        return filters;
    }
}