package com.yoviro.rest.service;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.models.repository.ResidentRepository;
import com.yoviro.rest.service.interfaces.IResidentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentServiceImpl implements IResidentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ResidentRepository residentRepository;

    @Override
    public Resident save(ResidentDTO residentDTO) {
        Resident resident = modelMapper.map(residentDTO, Resident.class);
        return residentRepository.save(resident);
    }

    @Override
    public Resident getOrCreateResident(ResidentDTO residentDTO) {
        ContactDTO contactDTO = residentDTO.getContact();
        OfficialIdDTO officialIdDTO = contactDTO.getOfficialIds().get(0);
        Resident resident = residentRepository.findByOfficialID(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
        return resident != null ? resident : save(residentDTO);
    }
}
