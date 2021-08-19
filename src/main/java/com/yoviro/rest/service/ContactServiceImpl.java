package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.OfficialIdType;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.repository.ContactRepository;
import com.yoviro.rest.models.repository.specification.handler.*;
import com.yoviro.rest.service.interfaces.IContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<ContactDTO> findAll() {
        return null;
    }

    @Override
    public Page<Contact> search(Pageable pageable, SearchContactDTO searchContactDTO) {
        //Define Search Criteria
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> contactCriteria = instanceContactCriteria(searchContactDTO); //Contact Filter
        List<SearchFilter> officialIDCriteria = OfficialIdServiceImpl.instanceContactSearchQry(searchContactDTO); //OfficialID Filter

        JoinColumnProps joinColumnPropsContactOfficialID = new JoinColumnProps();
        joinColumnPropsContactOfficialID.setJoinColumnName("officialIds");
        joinColumnPropsContactOfficialID.setSearchFilter(officialIDCriteria);

        //Add filters & joins
        qry.addJoinColumnProp(joinColumnPropsContactOfficialID);
        qry.setSearchFilter(contactCriteria);

        Specification<Contact> specification = SpecificationUtil.bySearchQuery(qry, Contact.class, Boolean.TRUE);
        return contactRepository.findAll(specification, pageable);
    }


    @Override
    public ContactDTO save(ContactDTO contactDto) {
        Contact contact = modelMapper.map(contactDto, Contact.class);
        contact = contactRepository.save(contact);
        return modelMapper.map(contact, ContactDTO.class);
    }

    @Override
    public void delete(Long id) {
        //contactoDao.deleteById(id);
    }

    @Override
    public Contact findContactByOfficialId(OfficialIdType officialIDType, String officialIDNumber) {
        return contactRepository.findByOfficialID(officialIDType, officialIDNumber);
    }

    @Override
    public Contact getOrCreateContact(ContactDTO contactDTO) {
        OfficialIdDTO officialIdDTO = contactDTO.getOfficialIds().get(0);
        Contact contact = findContactByOfficialId(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
        return contact != null ? contact : contactRepository.save(contact);
    }

    static List<SearchFilter> instanceContactCriteria(SearchContactDTO searchContactDTO) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter contactFilter;
        if (searchContactDTO.getFirstName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("firstName");
            contactFilter.setValue(searchContactDTO.getFirstName());
            contactFilter.setOperator(searchContactDTO.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);

            filters.add(contactFilter);
        }

        if (searchContactDTO.getSecondName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("secondName");
            contactFilter.setOperator(searchContactDTO.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);
            contactFilter.setValue(searchContactDTO.getSecondName());

            filters.add(contactFilter);
        }

        if (searchContactDTO.getFirstLastName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("firstLastName");
            contactFilter.setOperator(searchContactDTO.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);
            contactFilter.setValue(searchContactDTO.getFirstLastName());

            filters.add(contactFilter);
        }

        if (searchContactDTO.getSecondLastName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("secondLastName");
            contactFilter.setOperator(searchContactDTO.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);
            contactFilter.setValue(searchContactDTO.getSecondLastName());

            filters.add(contactFilter);
        }

        return filters;
    }
}