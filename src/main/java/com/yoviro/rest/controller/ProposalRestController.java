package com.yoviro.rest.controller;

import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.StatusProposalEnum;
import com.yoviro.rest.dto.search.SearchProductDTO;
import com.yoviro.rest.dto.search.SearchProposalDTO;
import com.yoviro.rest.models.entity.Person;
import com.yoviro.rest.models.entity.Proposal;
import com.yoviro.rest.service.interfaces.IProductService;
import com.yoviro.rest.service.interfaces.IProposalService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/proposal")
public class ProposalRestController {
    @Autowired
    IProposalService proposalService;

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam(required = false) String proposalNumber,
                                      @RequestParam(required = false) StatusProposalEnum status,
                                      @RequestParam(required = true) String page) {
        //Define Search criteria
        SearchProposalDTO criteria = new SearchProposalDTO();
        criteria.setProposalNumber(proposalNumber);
        criteria.setStatus(status);

        //Define Page criteria
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable pageableByProposalNumber = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("proposalNumber").ascending());

        Page<Proposal> pageResult = proposalService.search(pageableByProposalNumber, criteria);
        if (pageResult.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No proposals found");
        }

        //Define Response
        Map<String, Object> response = Map.ofEntries(
                Map.entry(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageResult)),
                Map.entry("content", wrapSearchResult(pageResult.getContent()))
        );
        return response;
    }

    private ArrayList<Map> wrapSearchResult(List<Proposal> proposals) {
        List dataList = new ArrayList<>();
        Map item = null;
        Person evaluator = null; //Only director can evaluate proposals
        Person storeKeeper = null;
        for (Proposal proposal : proposals) {
            storeKeeper = proposal.getAuthor().getPerson();

            //Evaluate null evaluator
            if (proposal.getEvaluator() != null) {
                evaluator = proposal.getEvaluator().getPerson();
            }

            item = Map.ofEntries(
                    Map.entry("proposalNumber", proposal.getProposalNumber()),
                    Map.entry("status", proposal.getStatus()),
                    Map.entry("storeKeeper", storeKeeper.getFullName()),
                    Map.entry("evaluator", evaluator == null ? Optional.empty() : evaluator.getFullName()),
                    Map.entry("updateTime", proposal.getUpdateAt()),
                    Map.entry("totalPrice", proposal.getTotalPrice().toString())
            );

            dataList.add(item);
        }
        return (ArrayList<Map>) dataList;
    }
}
