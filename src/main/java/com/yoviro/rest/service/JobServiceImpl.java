package com.yoviro.rest.service;

import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.dto.search.SearchJobDTO;
import com.yoviro.rest.dto.search.SearchResidentDTO;
import com.yoviro.rest.models.entity.Job;
import com.yoviro.rest.models.repository.JobAgreementRepository;
import com.yoviro.rest.models.repository.specification.handler.JoinColumnProps;
import com.yoviro.rest.models.repository.specification.handler.SearchFilter;
import com.yoviro.rest.models.repository.specification.handler.SearchQuery;
import com.yoviro.rest.models.repository.specification.handler.SpecificationUtil;
import com.yoviro.rest.service.interfaces.IJobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements IJobService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JobAgreementRepository jobAgreementRepository;

    @Override
    public Page<Job> search(Pageable pageable,
                            SearchJobDTO searchJobDTO) {
        SearchResidentDTO searchResidentDTO = searchJobDTO.getSearchResidentDTO();
        SearchContactDTO searchContactDTO = searchResidentDTO.getSearchPersonDTO();

        //Define Search Criteria
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> residentCriteria = ResidentServiceImpl.instanceResidentCriteria(searchResidentDTO); //Contact Filter
        List<SearchFilter> contactCriteria = ContactServiceImpl.instanceContactCriteria(searchContactDTO); //Contact Filter
        List<SearchFilter> officialIDCriteria = OfficialIdServiceImpl.instanceContactSearchQry(searchContactDTO); //OfficialID Filter

        JoinColumnProps joinColumnPropsContactOfficialID = new JoinColumnProps();
        joinColumnPropsContactOfficialID.setJoinColumnName("officialIds");
        joinColumnPropsContactOfficialID.setSearchFilter(officialIDCriteria);

        JoinColumnProps joinColumnPropsResidentAndContact = new JoinColumnProps();
        joinColumnPropsResidentAndContact.setJoinColumnName("person");
        joinColumnPropsResidentAndContact.setSearchFilter(contactCriteria);
        joinColumnPropsResidentAndContact.setSubJoinColumnProps(joinColumnPropsContactOfficialID);

        JoinColumnProps joinColumnPropsJobAndResident = new JoinColumnProps();
        joinColumnPropsJobAndResident.setJoinColumnName("resident");
        joinColumnPropsJobAndResident.setSearchFilter(residentCriteria);
        joinColumnPropsJobAndResident.setSubJoinColumnProps(joinColumnPropsResidentAndContact);

        //Add filters & joins
        qry.addJoinColumnProp(joinColumnPropsJobAndResident);
        Specification<Job> specification = SpecificationUtil.bySearchQuery(qry, Job.class, Boolean.TRUE);

        return jobAgreementRepository.findAll(specification, pageable);
    }

    static List<SearchFilter> instanceJobCriteria(SearchJobDTO searchJobDTO) {
        List<SearchFilter> filters = new ArrayList<>();
        return filters;
    }
}
