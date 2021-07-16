package com.yoviro.rest.config.mapper;

import com.yoviro.rest.dto.AgreementDTO;
import com.yoviro.rest.dto.JobDTO;
import com.yoviro.rest.dto.ResidentDTO;
import com.yoviro.rest.dto.SubmissionDTO;
import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Job;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;

import java.util.LinkedList;
import java.util.List;

public class MapperConfig {
    public static ModelMapper config(ModelMapper modelMapper) {
        //MAPPING JOB to JOB DTO ---------------------------------------------------------------------------------
        TypeMap<Agreement, AgreementDTO> typeMap = modelMapper.createTypeMap(Agreement.class, AgreementDTO.class);  //TYPE MAP
        Condition srcNotNullDestNull = ctx -> ctx.getSource() != null && ctx.getDestination() == null;              //CONDITION

        Converter<List<Job>, List<JobDTO>> jobConverter = new Converter<List<Job>, List<JobDTO>>() {
            @Override
            public List<JobDTO> convert(MappingContext<List<Job>, List<JobDTO>> mappingContext) {
                List<Job> src = mappingContext.getSource();
                List<JobDTO> dest = mappingContext.getDestination();
                if (src != null && dest == null) {
                    ModelMapper mapper = new ModelMapper();
                    LinkedList<JobDTO> jobDTOS = new LinkedList<JobDTO>();
                    for (Job jobRef : src) {
                        JobDTO submissionDTO = new SubmissionDTO();
                        submissionDTO.setId(jobRef.getId());
                        submissionDTO.setStartDate(jobRef.getStartDate());
                        submissionDTO.setEndDate(jobRef.getEndDate());
                        submissionDTO.setEffectiveDate(jobRef.getEffectiveDate());
                        submissionDTO.setJobNumber(jobRef.getJobNumber());
                        submissionDTO.setResident(mapper.map(jobRef.getResident(), ResidentDTO.class));

                        jobDTOS.add(submissionDTO);
                    }
                    return jobDTOS;
                }
                return null;
            }
        };

        typeMap.addMappings(mapper -> mapper.when(srcNotNullDestNull)
                                            .using(jobConverter)
                                            .map(Agreement::getJobs, AgreementDTO::setJobs));
        return modelMapper;
    }
}
