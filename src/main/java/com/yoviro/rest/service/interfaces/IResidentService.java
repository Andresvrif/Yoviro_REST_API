package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.dto.search.SearchResidentDTO;
import com.yoviro.rest.models.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IResidentService {
    public Resident save(ResidentDTO residentDTO);
    public Resident getOrCreateResident(ResidentDTO residentDTO);
    public Page<Resident> searchResident(SearchResidentDTO searchResidentDTO, Pageable pageable);
    Resident findByOfficialID(OfficialIdDTO officialIdDTO);
}
