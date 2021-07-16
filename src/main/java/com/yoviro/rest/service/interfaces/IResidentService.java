package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.models.entity.Resident;

public interface IResidentService {
    public Resident save(ResidentDTO residentDTO);
    public Resident getOrCreateResident(ResidentDTO residentDTO);
}
