package com.yoviro.rest.config.mapper;

import com.yoviro.rest.dto.ContractorDTO;
import com.yoviro.rest.dto.PersonDTO;
import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.models.entity.Resident;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public class ResidentToDTOConverter implements Converter<Resident, ResidentDTO> {
    @Override
    public ResidentDTO convert(MappingContext<Resident, ResidentDTO> mappingContext) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new ContactToDTOConverter());

        Resident src = mappingContext.getSource();
        ResidentDTO dest = mappingContext.getDestination();
        if (src != null && dest == null) {
            ResidentDTO residentDTO = new ResidentDTO();
            residentDTO.setId(src.getId());
            residentDTO.setPerson(modelMapper.map(src.getPerson(), PersonDTO.class));
            residentDTO.setEnable(src.getEnable());
            residentDTO.setCreateAt(src.getCreateAt());

            return residentDTO;
        }
        return null;
    }
}
