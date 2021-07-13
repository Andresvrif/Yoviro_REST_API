package com.yoviro.rest.dto.mixer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoviro.rest.dto.OfficialIdDTO;

import java.util.List;

public class OfficialIDMixin {
    @JsonCreator
    public OfficialIDMixin(@JsonProperty("officialIds") List<OfficialIdDTO> officialIds) {
    }
}
