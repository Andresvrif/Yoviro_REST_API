package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
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
import com.yoviro.rest.models.entity.OfficialId;
import com.yoviro.rest.models.entity.Person;
import com.yoviro.rest.service.interfaces.IContactService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contacts")
public class ContactRestController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private IContactService contactService;

    @PostMapping
    public ContactDTO newContact(@RequestBody String json) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(json);
        Boolean isPerson = jsonObject.has("personDTO");
        Boolean isCompany = jsonObject.has("companyDTO");
        if (isPerson) {
            PersonDTO personDTO = objectMapper.readValue(jsonObject.get("personDTO").toString(), PersonDTO.class);
            return contactService.createOrUpdate(personDTO);
        } else if (isCompany) {
            return null;
        } else {
            throw new IllegalArgumentException("Type of ContactDTO not valid!");
        }
    }

    @GetMapping("/findContactByOfficialID")
    public Map<String, Object> findContactByOfficialID(@RequestParam(required = true, name = "officialIdType") OfficialIdTypeEnum officialIdTypeEnum,
                                                       @RequestParam(required = true) String officialIdNumber) throws JsonProcessingException, JSONException {
        OfficialIdDTO officialIdDTO = new OfficialIdDTO();
        officialIdDTO.setOfficialIdType(officialIdTypeEnum);
        officialIdDTO.setOfficialIdNumber(officialIdNumber);
        Contact contact = contactService.findContactByOfficialId(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
        if (contact == null) return null;
        if (contact instanceof Person) {
            return JSONUtil.addRootName("ContactDTO", modelMapper.map(contact, PersonDTO.class));
        } else {
            return JSONUtil.addRootName("ContactDTO", modelMapper.map(contact, CompanyDTO.class));
        }
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam(required = false) String contactType,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String secondName,
                                      @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String secondLastName,
                                      @RequestParam(required = false) OfficialIdTypeEnum officialIDType,
                                      @RequestParam(required = false) String officialIDNumber,
                                      @RequestParam(required = true) Boolean exactCoincidence,
                                      @RequestParam(required = true) String page) throws JsonProcessingException, JSONException {
        Boolean isPerson = Boolean.FALSE;
        Boolean isCompany = Boolean.FALSE;
        if (contactType != null) {
            isPerson = contactType.compareToIgnoreCase("PERSON") == 0;
            isCompany = contactType.compareToIgnoreCase("COMPANY") == 0;
        }

        SearchContactDTO searchContactDTO = null;
        if (isPerson) {
            SearchPersonDTO searchPersonDTO = new SearchPersonDTO();
            searchPersonDTO.setName(name);
            searchPersonDTO.setSecondName(secondName);
            searchPersonDTO.setLastName(lastName);
            searchPersonDTO.setSecondLastName(secondLastName);
            searchPersonDTO.setOfficialIDType(officialIDType);
            searchPersonDTO.setOfficialIDNumber(officialIDNumber);
            searchPersonDTO.setExactCoincidence(exactCoincidence);

            searchContactDTO = searchPersonDTO;
        } else if (isCompany) {
            SearchCompanyDTO searchCompanyDTO = new SearchCompanyDTO();
            searchCompanyDTO.setName(name);
            searchCompanyDTO.setExactCoincidence(exactCoincidence);
            searchContactDTO = searchCompanyDTO;
        } else {
            searchContactDTO = new SearchContactDTO() {
            };

            searchContactDTO.setName(name);
            searchContactDTO.setOfficialIDType(officialIDType);
            searchContactDTO.setOfficialIDNumber(officialIDNumber);
        }

        //Define Pageable
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable sortedByFirstName = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("name").ascending());

        //Call Controller
        Page pageContacts = contactService.search(sortedByFirstName, searchContactDTO);
        if (pageContacts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Contact not found");
        }

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageContacts));
        if (isPerson) {
            response.put("contacts", pageContacts.getContent().stream().map(c -> modelMapper.map(c, PersonDTO.class)).collect(Collectors.toList()));
        } else if (isCompany) {
            response.put("contacts", pageContacts.getContent().stream().map(c -> modelMapper.map(c, CompanyDTO.class)).collect(Collectors.toList()));
        } else {
            response.put("contacts", wrapGeneralContactInformation(pageContacts.getContent()));
        }

        return response;
    }

    private List<HashMap> wrapGeneralContactInformation(List<Contact> contacts) {
        List response = new ArrayList<>();
        Map content = null;
        OfficialId officialId = null;

        for (Contact contact : contacts) {
            officialId = contact.getPrimaryOfficialID();
            if (contact instanceof Person) {
                content = Map.ofEntries(
                        //Map.entry("id", (contact).getId()),
                        Map.entry("fullName", ((Person) contact).getFullName()),
                        Map.entry("officialIdType", officialId.getOfficialIdType()),
                        Map.entry("officialIdNumber", officialId.getOfficialIdNumber()),
                        Map.entry("email", Optional.ofNullable(contact.getEmail())),
                        Map.entry("contactType", "PERSON")
                );
            } else if (contact instanceof Company) {
                content = Map.ofEntries(
                        //Map.entry("id", (contact).getId()),
                        Map.entry("fullName", ((Company) contact).getName()),
                        Map.entry("officialIdType", officialId.getOfficialIdType()),
                        Map.entry("officialIdNumber", officialId.getOfficialIdNumber()),
                        Map.entry("email", Optional.ofNullable(contact.getEmail())),
                        Map.entry("contactType", "COMPANY")
                );
            }

            response.add(content);
        }

        return response;
    }
}