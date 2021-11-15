package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.dto.CompanyDTO;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.PersonDTO;
import com.yoviro.rest.dto.search.SearchCompanyDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.dto.search.SearchPersonDTO;
import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.CompanyRepository;
import com.yoviro.rest.models.repository.ContactRepository;
import com.yoviro.rest.models.repository.OfficialIdRepository;
import com.yoviro.rest.models.repository.PersonRepository;
import com.yoviro.rest.models.repository.specification.handler.*;
import com.yoviro.rest.service.interfaces.IContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private OfficialIdRepository officialIdRepository;

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
        } else {
            //Generic Search
            //The SYSTEM is gonna search based in generic field like name, email & official id
            Specification<Contact> specification = SpecificationUtil.bySearchQuery(qry, Contact.class, Boolean.TRUE);
            page = contactRepository.findAll(specification, pageable);
        }

        return page;
    }

    static List<SearchFilter> instanceContactCriteria(SearchContactDTO searchContactDTO) {
        if (searchContactDTO instanceof SearchPersonDTO) {
            return instancePersonCriteria((SearchPersonDTO) searchContactDTO);
        } else if (searchContactDTO instanceof SearchCompanyDTO) {
            return instanceCompanyCriteria((SearchCompanyDTO) searchContactDTO);
        } else {
            return instanceGeneralCriteria(searchContactDTO);
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

    static List<SearchFilter> instanceCompanyCriteria(SearchCompanyDTO criteria) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter companyFilter;
        if (criteria.getName() != null) {
            companyFilter = new SearchFilter();
            companyFilter.setProperty("name");
            companyFilter.setValue(criteria.getName());
            companyFilter.setOperator(criteria.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);

            filters.add(companyFilter);
        }

        return filters;
    }

    static List<SearchFilter> instanceGeneralCriteria(SearchContactDTO criteria) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter contactFilter;
        if (criteria.getName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("name");
            contactFilter.setValue(criteria.getName());
            contactFilter.setOperator(criteria.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);

            filters.add(contactFilter);
        }

        if (criteria.getName() != null) {
            contactFilter = new SearchFilter();
            contactFilter.setProperty("email");
            contactFilter.setValue(criteria.getEmail());
            contactFilter.setOperator(criteria.getExactCoincidence() ? OperatorEnum.EQUALS : OperatorEnum.LIKE);

            filters.add(contactFilter);
        }

        return filters;
    }

    @Transactional
    @Override
    public ContactDTO createOrUpdate(ContactDTO contactDTO) {
        if (contactDTO instanceof PersonDTO) {
            return createOrUpdatePerson((PersonDTO) contactDTO);
        } else if (contactDTO instanceof CompanyDTO) {
            return null;
        } else {
            throw new ResponseStatusException(HttpStatus.CHECKPOINT, "Not contact type mapped");
        }

        /*
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
        */
    }

    private PersonDTO createOrUpdatePerson(PersonDTO personDTO) {
        Boolean isNew = personDTO.getId() == null;
        Person person;
        Optional<Contact> optionalContact;

        if (isNew) {
            person = modelMapper.map(personDTO, Person.class);
            person = contactRepository.save(person);
            return modelMapper.map(person, PersonDTO.class);
        } else {
            optionalContact = contactRepository.findById(personDTO.getId());
            if (optionalContact.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found");

            person = (Person) optionalContact.get();

            //Map Person Info
            person.setName(personDTO.getFirstName());
            person.setSecondName(personDTO.getSecondName());
            person.setLastName(personDTO.getLastName());
            person.setSecondLastName(personDTO.getSecondLastName());
            person.setPhoto(personDTO.getPhoto());
            person.setEmail(personDTO.getEmail());

            //Update Official IDs
            createOrUpdateOfficialIDs(person, personDTO.getOfficialIds());
            person = contactRepository.save(person);
            return modelMapper.map(person, PersonDTO.class);
        }
    }

    private void createOrUpdateOfficialIDs(Person personToRelate,
                                           List<OfficialIdDTO> officialIdDTOS) {
        //IDs
        List<Long> officialIDs = officialIdDTOS.stream().filter(e -> e.getId() != null).map(e -> e.getId()).collect(Collectors.toList());
        List<OfficialId> toBeKeeped = personToRelate.getOfficialIds().stream().filter(e -> officialIDs.contains(e.getId())).collect(Collectors.toList());
        List<OfficialId> toBeRemoved = personToRelate.getOfficialIds().stream().filter(e -> !toBeKeeped.contains(e)).collect(Collectors.toList());
        List<OfficialIdDTO> toBeAdded = officialIdDTOS.stream().filter(e -> e.getId() == null).collect(Collectors.toList());

        //Adds
        OfficialId officialId;
        for (OfficialIdDTO officialIdDTO : toBeAdded){
            officialId = new OfficialId();
            officialId.setContact(personToRelate);
            officialId.setOfficialIdNumber(officialIdDTO.getOfficialIdNumber());
            officialId.setOfficialIdType(officialIdDTO.getOfficialIdType());
            officialId.setPrimaryOfficialId(officialIdDTO.getPrimaryOfficialId());
            officialId = officialIdRepository.save(officialId);

            personToRelate.getOfficialIds().add(officialId);
        }

        //Updates
        OfficialIdDTO updateInfo;
        for (OfficialId officialIdToBeUpdated : toBeKeeped) {
            updateInfo = officialIdDTOS.stream().filter(e -> e.getId() == officialIdToBeUpdated.getId()).findFirst().get();
            if (updateInfo == null) continue;
            officialIdToBeUpdated.setOfficialIdType(updateInfo.getOfficialIdType());
            officialIdToBeUpdated.setOfficialIdNumber(updateInfo.getOfficialIdNumber());
            officialIdToBeUpdated.setPrimaryOfficialId(updateInfo.getPrimaryOfficialId());
        }

        //Remove Flow
        toBeRemoved.forEach(e -> {
            officialIdRepository.delete(e);
        });
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