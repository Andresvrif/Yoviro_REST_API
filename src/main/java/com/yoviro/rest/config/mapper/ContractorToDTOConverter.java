package com.yoviro.rest.config.mapper;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.ContractorDTO;
import com.yoviro.rest.dto.PersonDTO;
import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.models.entity.Person;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;

public class ContractorToDTOConverter implements Converter<Contractor, ContractorDTO> {

    @Override
    public ContractorDTO convert(MappingContext<Contractor, ContractorDTO> mappingContext) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new ContactToDTOConverter());

        Contractor src = mappingContext.getSource();
        ContractorDTO dest = mappingContext.getDestination();
        if (src != null && dest == null) {
            ContractorDTO contractorDTO = new ContractorDTO();
            contractorDTO.setId(src.getId());
            contractorDTO.setContractorNumber(src.getContractorNumber());
            contractorDTO.setCreateAt(src.getCreateAt());
            contractorDTO.setPerson(modelMapper.map(src.getPerson(), PersonDTO.class));

            return contractorDTO;
        }
        return null;
    }
}
