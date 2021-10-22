package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.search.SearchProposalDTO;
import com.yoviro.rest.models.entity.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface IProposalService {
    Page<Proposal> search(Pageable pageable, SearchProposalDTO criteria);
}
