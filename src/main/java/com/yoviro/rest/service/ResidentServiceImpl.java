package com.yoviro.rest.service;

import com.yoviro.rest.dto.ActivityDTO;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.dto.search.SearchResidentDTO;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.models.repository.ActivityRepository;
import com.yoviro.rest.models.repository.ResidentRepository;
import com.yoviro.rest.models.repository.specification.handler.*;
import com.yoviro.rest.service.interfaces.IContactService;
import com.yoviro.rest.service.interfaces.IResidentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResidentServiceImpl implements IResidentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    private IContactService contactService;

    @Override
    public Resident save(ResidentDTO residentDTO) {
        Resident resident = modelMapper.map(residentDTO, Resident.class);
        return residentRepository.save(resident);
    }

    @Override
    public Resident getOrCreateResident(ResidentDTO residentDTO) {
        ContactDTO contactDTO = residentDTO.getContact();
        OfficialIdDTO officialIdDTO = contactDTO.getOfficialIds().get(0);
        Resident resident = residentRepository.findByOfficialID(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
        if (resident == null) {
            Contact contact = contactService.getOrCreateContact(contactDTO);
            resident = new Resident();
            resident.setEnable(Boolean.TRUE);
            //TODO Refactor
            //resident.setContact(contact);

            return resident;
        } else {
            return resident;
        }
    }

    @Override
    public Page<Resident> searchResident(SearchResidentDTO searchResidentDTO,
                                         Pageable pageable) {
        SearchContactDTO searchContactDTO = searchResidentDTO.getSearchContactDTO();

        //Define Search Criteria
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> contactCriteria = ContactServiceImpl.instanceContactCriteria(searchContactDTO); //Contact Filter
        List<SearchFilter> officialIDCriteria = OfficialIdServiceImpl.instanceContactSearchQry(searchContactDTO); //OfficialID Filter

        JoinColumnProps joinColumnPropsContactOfficialID = new JoinColumnProps();
        joinColumnPropsContactOfficialID.setJoinColumnName("officialIds");
        joinColumnPropsContactOfficialID.setSearchFilter(officialIDCriteria);

        JoinColumnProps joinColumnPropsResidentAndContact = new JoinColumnProps();
        joinColumnPropsResidentAndContact.setJoinColumnName("contact");
        joinColumnPropsResidentAndContact.setSearchFilter(contactCriteria);
        joinColumnPropsResidentAndContact.setSubJoinColumnProps(joinColumnPropsContactOfficialID);

        qry.addJoinColumnProp(joinColumnPropsResidentAndContact);
        Specification<Resident> specification = SpecificationUtil.bySearchQuery(qry, Resident.class, Boolean.TRUE);

        return residentRepository.findAll(specification, pageable);
    }

    @Override
    public Resident findByOfficialID(OfficialIdDTO officialIdDTO) {
        return residentRepository.findByOfficialID(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
    }

    @Override
    public Resident findByActivity(ActivityDTO activityDTO) {
        Optional<Activity> activityOptional = activityRepository.findById(activityDTO.getId());
        if(activityOptional.isEmpty()){
            return null;
        }
        Activity activity = activityOptional.get();
        
        return activity.getJob().getResident();
    }

    static List<SearchFilter> instanceResidentCriteria(SearchResidentDTO searchResidentDTO) {
        List<SearchFilter> filters = new ArrayList<>();
        return filters;
    }
}