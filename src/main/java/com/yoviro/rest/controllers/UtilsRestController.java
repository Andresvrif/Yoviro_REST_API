package com.yoviro.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/utils")
public class UtilsRestController {

    @GetMapping(value = "/availableOfficialIDs")
    public List<String> availableOfficialIDs() {
        return null;
    }
}
