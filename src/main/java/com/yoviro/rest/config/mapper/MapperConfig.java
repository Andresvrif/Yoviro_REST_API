package com.yoviro.rest.config.mapper;

import com.yoviro.rest.dto.*;
import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.models.entity.Job;
import com.yoviro.rest.models.entity.Resident;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/***
 * Author : Andrés V.
 * Desc : Configuration for model mapper at App level
 */
public class MapperConfig {

    public static ModelMapper config(ModelMapper modelMapper) {
        //Job List MAPPER in AGREEMENT ------------------------------------------------------------------------
        TypeMap<Agreement, AgreementDTO> agreementTypeMap = modelMapper.createTypeMap(Agreement.class, AgreementDTO.class);  //TYPE MAP for Agreement
        agreementTypeMap.addMappings(mapper -> {
            mapper.using(new ContractorToDTOConverter())
                    .map(Agreement::getContractor, AgreementDTO::setContractor);

            mapper.when(ctx -> ctx.getSource() != null && ctx.getDestination() == null)
                    .using(new JobListConverter())
                    .map(Agreement::getJobs, AgreementDTO::setJobs);


        });

        //Contact MAPPER in RESIDENT --------------------------------------------------------------------------
        TypeMap<Resident, ResidentDTO> residentTypeMap = modelMapper.createTypeMap(Resident.class, ResidentDTO.class);  //TYPE MAP for Agreement
        residentTypeMap.addMappings(mapping -> {
            mapping.using(new ContactToDTOConverter())
                    .map(Resident::getPerson, ResidentDTO::setPerson);
        });

        return modelMapper;
    }
}
