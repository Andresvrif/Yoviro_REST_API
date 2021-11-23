package com.yoviro.rest.service;

import com.yoviro.rest.dto.search.SearchPurchaseOrderDTO;
import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.models.entity.PurchaseOrder;
import com.yoviro.rest.models.repository.PurchaseOrderRepository;
import com.yoviro.rest.models.repository.specification.handler.*;
import com.yoviro.rest.service.interfaces.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public Page<PurchaseOrder> search(Pageable pageable,
                                      SearchPurchaseOrderDTO criteria) {
        //Define Search Criteria
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> purchaseOrderCriteria = instancePurchaseOrderCriteria(criteria);
        List<SearchFilter> proposalCriteria = ProposalServiceImpl.instanceProposalCriteria(criteria.getProposalCriteria());

        JoinColumnProps joinColumnProposal = new JoinColumnProps();
        joinColumnProposal.setJoinColumnName("proposals");
        joinColumnProposal.setSearchFilter(proposalCriteria);

        //Add filters & joins
        qry.addJoinColumnProp(joinColumnProposal);
        qry.setSearchFilter(purchaseOrderCriteria);

        Specification<PurchaseOrder> specification = SpecificationUtil.bySearchQuery(qry, PurchaseOrder.class, Boolean.TRUE);
        return purchaseOrderRepository.findAll(specification, pageable);
    }

    @Override
    public PurchaseOrder findByPurcharseOrderNumber(String purchaseOrderNumber) {
        return purchaseOrderRepository.findByPurchaseOrderNumber(purchaseOrderNumber);
    }

    static List<SearchFilter> instancePurchaseOrderCriteria(SearchPurchaseOrderDTO criteria) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter purchaseOrderFilter;
        if (criteria.getStatus() != null) {
            purchaseOrderFilter = new SearchFilter();
            purchaseOrderFilter.setProperty("status");
            purchaseOrderFilter.setValue(criteria.getStatus());
            purchaseOrderFilter.setOperator(OperatorEnum.EQUALS);

            filters.add(purchaseOrderFilter);
        }

        return filters;
    }
}
