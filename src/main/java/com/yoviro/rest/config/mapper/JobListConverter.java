package com.yoviro.rest.config.mapper;

import com.yoviro.rest.dto.*;
import com.yoviro.rest.models.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import java.util.LinkedList;
import java.util.List;

public class JobListConverter implements Converter<List<Job>, List<JobDTO>> {
    @Override
    public List<JobDTO> convert(MappingContext<List<Job>, List<JobDTO>> mappingContext) {
        List<Job> src = mappingContext.getSource();
        List<JobDTO> dest = mappingContext.getDestination();
        if (src != null && dest == null) {
            //Pre define resident handle

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.addConverter(new ContactToDTOConverter());


            //Job list treatment
            LinkedList<JobDTO> jobDTOS = new LinkedList<JobDTO>();
            for (Job jobRef : src) {
                if (jobRef instanceof Submission) {
                    JobDTO submissionDTO = new SubmissionDTO();
                    submissionDTO.setId(jobRef.getId());
                    submissionDTO.setStartDate(jobRef.getStartDate());
                    submissionDTO.setEndDate(jobRef.getEndDate());
                    submissionDTO.setEffectiveDate(jobRef.getEffectiveDate());
                    submissionDTO.setJobNumber(jobRef.getJobNumber());
                    submissionDTO.setResident(modelMapper.map(jobRef.getResident(), ResidentDTO.class));

                    jobDTOS.add(submissionDTO);
                }
            }
            return jobDTOS;
        }
        return null;
    }
}
