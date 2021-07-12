package com.yoviro.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.models.entity.Contacto;
import com.yoviro.rest.models.service.interfaces.IContactoService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/contactos")
public class ContactoRestController {

    @Autowired
    private IContactoService contactoService;

    @PostMapping
    public Contacto nuevoContacto(@RequestBody String json) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Contacto contacto = objectMapper.readValue(jsonObject.get("contacto").toString(), Contacto.class);

        return contactoService.save(contacto);
    }
}