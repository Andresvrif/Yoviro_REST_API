package com.yoviro.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.dto.ActivityDTO;
import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.dto.search.SearchResidentDTO;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.OfficialId;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.service.interfaces.IResidentService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/residents")
public class ResidentRestController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private IResidentService residentService;

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String secondName,
                                      @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String secondLastName,
                                      @RequestParam(required = false) OfficialIdTypeEnum officialIDTypeEnum,
                                      @RequestParam(required = false) String officialIDNumber,
                                      @RequestParam(required = true) Boolean exactCoincidence,
                                      @RequestParam(required = true) String page) throws JsonProcessingException, JSONException {
        SearchContactDTO searchContactDTO = new SearchContactDTO();
        searchContactDTO.setFirstName(firstName);
        searchContactDTO.setSecondName(secondName);
        searchContactDTO.setLastName(lastName);
        searchContactDTO.setSecondLastName(secondLastName);
        searchContactDTO.setOfficialIDType(officialIDTypeEnum);
        searchContactDTO.setOfficialIDNumber(officialIDNumber);
        searchContactDTO.setExactCoincidence(exactCoincidence);

        SearchResidentDTO searchResidentDTO = new SearchResidentDTO();
        searchResidentDTO.setSearchContactDTO(searchContactDTO);

        //Define Pageable
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable sortedByContact = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("contact").ascending());

        //Call Controller
        Page<Resident> pageResidents = residentService.searchResident(searchResidentDTO, sortedByContact);

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageResidents));
        response.put("residents", pageResidents.getContent().stream().map(c -> modelMapper.map(c, ResidentDTO.class)).collect(Collectors.toList()));
        return response;
    }

    @GetMapping("/bringResidentByIdActivity")
    public Map<String, Object> bringResidentAssociated(@RequestParam String idActivity) {
        //Mapear Activity DTO
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setId(Long.valueOf(idActivity));

        Resident resident = residentService.findByActivity(activityDTO);
        if (resident == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resident not found");
        }

        //Define Response
        var response = wrapResident(resident);
        //reurn modelMapper.map(resident, ResidentDTO.class);
        return response;
    }

    private HashMap<String, Object> wrapResident(Resident resident) {
        Contact contact = null;
        //resident.getContact(); TODO Refactor
        OfficialId officialId = contact.getPrimaryOfficialID();

        HashMap<String, Object> response = new HashMap<>();

        //Map Official ID Info
        response.put("Resident", Map.ofEntries(
                //TODO REFACTOR
                //Map.entry("fullName", contact.getFullName()),
                Map.entry("OfficialIdDto", Map.ofEntries(
                        Map.entry("officialIdType", officialId.getOfficialIdType()),
                        Map.entry("officialIdNumber", officialId.getOfficialIdNumber())
                ))
        ));

        return response;
    }
}