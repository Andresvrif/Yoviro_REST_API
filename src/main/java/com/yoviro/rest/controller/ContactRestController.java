package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.PropertiesConfig;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.service.interfaces.IContactService;
import com.yoviro.rest.util.JSONUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
public class ContactRestController {

    @Autowired
    PropertiesConfig propertiesConfig;

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
    public Map<String, Object> findContactByOfficialID(@RequestParam Map<String, String> params) throws JsonProcessingException, JSONException {
        OfficialIdDTO officialIdDTO = new OfficialIdDTO();
        officialIdDTO.setOfficialIdType(params.get("officialIdType"));
        officialIdDTO.setOfficialIdNumber(params.get("officialIdNumber"));
        Contact contact = contactService.findContactByOfficialId(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());

        if (contact == null) return null;
        return JSONUtil.addRootName(modelMapper.map(contact, ContactDTO.class));
    }
}