package com.yoviro.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.service.interfaces.IContactService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/contacts")
public class ContactRestController {

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
}