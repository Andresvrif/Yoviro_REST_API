package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Proposal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends PagingAndSortingRepository<Proposal, Long>, JpaSpecificationExecutor<Proposal> {
    Proposal findProposalsByProposalNumber(String proposalNumber);
}
