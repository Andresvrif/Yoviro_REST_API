package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.models.entity.Contact;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        ContactDTO contactDto = objectMapper
                .readValue(jsonObject.get("contactDTO").toString(), ContactDTO.class);

        return contactService.save(contactDto);
    }

    @GetMapping("/findContactByOfficialID")
    public Map<String, Object> findContactByOfficialID(@RequestParam(required = true) OfficialIdTypeEnum officialIdTypeEnum,
                                                       @RequestParam(required = true) String officialIdNumber) throws JsonProcessingException, JSONException {
        OfficialIdDTO officialIdDTO = new OfficialIdDTO();
        officialIdDTO.setOfficialIdType(officialIdTypeEnum);
        officialIdDTO.setOfficialIdNumber(officialIdNumber);
        Contact contact = contactService.findContactByOfficialId(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());

        if (contact == null) return null;
        return JSONUtil.addRootName(modelMapper.map(contact, ContactDTO.class));
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String secondName,
                                      @RequestParam(required = false) String firstLastName,
                                      @RequestParam(required = false) String secondLastName,
                                      @RequestParam(required = false) OfficialIdTypeEnum officialIDTypeEnum,
                                      @RequestParam(required = false) String officialIDNumber,
                                      @RequestParam(required = true) Boolean exactCoincidence,
                                      @RequestParam(required = true) String page) throws JsonProcessingException, JSONException {
        SearchContactDTO searchContactDTO = new SearchContactDTO();
        searchContactDTO.setFirstName(firstName);
        searchContactDTO.setSecondName(secondName);
        searchContactDTO.setFirstLastName(firstLastName);
        searchContactDTO.setSecondLastName(secondLastName);
        searchContactDTO.setOfficialIDType(officialIDTypeEnum);
        searchContactDTO.setOfficialIDNumber(officialIDNumber);
        searchContactDTO.setExactCoincidence(exactCoincidence);

        //Define Pageable
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable sortedByFirstName = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("firstName").ascending());

        //Call Controller
        Page<Contact> pageContacts = contactService.search(sortedByFirstName, searchContactDTO);

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageContacts));
        response.put("contacts", pageContacts.getContent().stream().map(c -> modelMapper.map(c, ContactDTO.class)).collect(Collectors.toList()));

        return response;
    }
}