package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.dto.CompanyDTO;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.PersonDTO;
import com.yoviro.rest.dto.search.SearchCompanyDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.dto.search.SearchPersonDTO;
import com.yoviro.rest.models.entity.Company;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.Person;
import com.yoviro.rest.models.repository.CompanyRepository;
import com.yoviro.rest.models.repository.ContactRepository;
import com.yoviro.rest.models.repository.PersonRepository;
import com.yoviro.rest.models.repository.specification.handler.*;
import com.yoviro.rest.service.interfaces.IContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<ContactDTO> findAll() {
        return null;
    }

    @Override
    public Page search(Pageable pageable,
                       SearchContactDTO searchContactDTO) {
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

        //Execute query
        Page page = null;
        if (searchContactDTO instanceof SearchPersonDTO) {
            Specification<Person> specification = SpecificationUtil.bySearchQuery(qry, Person.class, Boolean.TRUE);
            page = personRepository.findAll(specification, pageable);
        } else if (searchContactDTO instanceof SearchCompanyDTO) {
            Specification<Company> specification = SpecificationUtil.bySearchQuery(qry, Company.class, Boolean.TRUE);
            page = companyRepository.findAll(specification, pageable);
        }

        return page;
    }

    static List<SearchFilter> instanceContactCriteria(SearchContactDTO searchContactDTO) {
        if (searchContactDTO instanceof SearchPersonDTO) {
            return instancePersonCriteria((SearchPersonDTO) searchContactDTO);
        } else if (searchContactDTO instanceof SearchCompanyDTO) {
            //TODO Mapeo para compañia
            throw new IllegalArgumentException("PENDIENTE DE REALIZAR - TODO");
        } else {
            throw new IllegalArgumentException("NO EXISTE TIPO DE CRITERIO DE BUSQUEDA");
        }
    }

    static List<SearchFilter> instancePersonCriteria(SearchPersonDTO searchPersonDTO) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter contactFilter;
        if (searchPersonDTO.getName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("name");
            contactFilter.setValue(searchPersonDTO.getName());
            contactFilter.setOperator(searchPersonDTO.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);

            filters.add(contactFilter);
        }

        if (searchPersonDTO.getSecondName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("secondName");
            contactFilter.setOperator(searchPersonDTO.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);
            contactFilter.setValue(searchPersonDTO.getSecondName());

            filters.add(contactFilter);
        }

        if (searchPersonDTO.getLastName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("lastName");
            contactFilter.setOperator(searchPersonDTO.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);
            contactFilter.setValue(searchPersonDTO.getLastName());

            filters.add(contactFilter);
        }

        if (searchPersonDTO.getSecondLastName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("secondLastName");
            contactFilter.setOperator(searchPersonDTO.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);
            contactFilter.setValue(searchPersonDTO.getSecondLastName());

            filters.add(contactFilter);
        }

        return filters;
    }

    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        if (contactDTO instanceof PersonDTO) {
            Person person = modelMapper.map(contactDTO, Person.class);
            person = contactRepository.save(person);
            return modelMapper.map(person, PersonDTO.class);
        } else if (contactDTO instanceof CompanyDTO) {
            Company company = modelMapper.map(contactDTO, Company.class);
            company = contactRepository.save(company);
            return modelMapper.map(company, CompanyDTO.class);
        } else {
            throw new IllegalArgumentException("Type of ContactDTO not valid!, please review this object: " + contactDTO.getClass().getSimpleName());
        }
    }

    @Override
    public void delete(Long id) {
        //contactoDao.deleteById(id);
    }

    @Override
    public Contact findContactByOfficialId(OfficialIdTypeEnum officialIDTypeEnum, String officialIDNumber) {
        return contactRepository.findByOfficialID(officialIDTypeEnum, officialIDNumber);
    }

    @Override
    public Contact getOrCreateContact(ContactDTO contactDTO) throws Exception {
        OfficialIdDTO officialIdDTO = contactDTO.getOfficialIds().get(0);
        Contact contact = findContactByOfficialId(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
        if (contact != null) return contact;
        if (contactDTO instanceof PersonDTO) {
            contact = modelMapper.map(contactDTO, Person.class);
        } else if (contactDTO instanceof CompanyDTO) {
            contact = modelMapper.map(contactDTO, Company.class);
        } else {
            throw new Exception("Not contact type enable");
        }

        return contactRepository.save(contact);
    }
}