package com.yoviro.rest.service;

import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Job;
import com.yoviro.rest.models.repository.ActivityPatternRepository;
import com.yoviro.rest.models.repository.AgreementRepository;
import com.yoviro.rest.models.repository.JobAgreementRepository;
import com.yoviro.rest.models.repository.projections.AgreementResidentProjection;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityPatternServiceImpl implements IActivityPatternService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ActivityPatternRepository activityPatternRepository;

    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    JobAgreementRepository jobAgreementRepository;

    @Override
    public Iterable<ActivityPattern> findAll() {
        return activityPatternRepository.findAll();
    }

    @Override
    public ActivityPattern edit(ActivityPatternDTO activityPatternDTO) {
        return null;
    }

    @Override
    public ActivityPattern save(ActivityPatternDTO activityPatternDTO) {
        ActivityPattern activityPattern = modelMapper.map(activityPatternDTO, ActivityPattern.class);
        return activityPatternRepository.save(activityPattern);
    }

    @Override
    public void deleteActivityPatternByPatternCode(String patternCode) {
        activityPatternRepository.deleteActivityPatternByPatternCode(patternCode);
    }

    @Override
    public void deleteAllByPatternCodes(String[] patternCodesToDelete) {
        activityPatternRepository.deleteByPatternCodes(Arrays.asList(patternCodesToDelete));
    }

    @Override
    public Page<SummaryActivityPatternProjection> summaryList(Pageable pageable) {
        return activityPatternRepository.findAllByIdNotNull(pageable);
    }

    @Override
    public Page<SummaryActivityPatternProjection> summaryList(Pageable pageable, String subject) {
        subject = "%".concat(subject).concat("%");
        return activityPatternRepository.findAllBySubjectLike(pageable, subject);
    }

    @Override
    public Page<AgreementResidentProjection> agreementsResidentRelated(Pageable pageable, String patternCode) {
        return activityPatternRepository.agreementsResidentRelated(pageable, patternCode);
    }

    @Override
    public List<ActivityPattern> bringCandidatesToCreateActivities() {
        //Bring activitPatterns with agreements
        List<ActivityPattern> activityPatterns = activityPatternRepository.enableActivityPatternsWithAgreements();

        return activityPatterns;
    }

    @Transactional
    @Override
    public ActivityPattern createOrUpdateActivityPatternWithAgreements(ActivityPatternDTO activityPatternDTO,
                                                                       List<String> agreementNumbers) {
        //Bring all agreements
        List<Agreement> agreements = agreementRepository.findAgreementByAgreementNumberIn(agreementNumbers);

        //Get or Create activity Pattern
        ActivityPattern activityPattern = activityPatternRepository.findActivityPatternByPatternCode(activityPatternDTO.getPatternCode());
        if (activityPattern == null) {
            //It doesn't exist, create it
            activityPattern = modelMapper.map(activityPatternDTO, ActivityPattern.class);
            activityPattern = activityPatternRepository.save(activityPattern);
        } else {
            //It exist, update it
            activityPattern.setEnable(activityPatternDTO.getEnable());
            activityPattern.setDayFrequency(activityPatternDTO.getDayFrequency());
            activityPattern.setHourFrequency(activityPatternDTO.getHourFrequency());
            activityPattern.setStartDate(activityPatternDTO.getStartDate());
            activityPattern.setEndDate(activityPatternDTO.getEndDate());
            activityPattern.setSubject(activityPatternDTO.getSubject());
            activityPattern.setDescription(activityPatternDTO.getDescription());
            activityPattern.setColorCode(activityPatternDTO.getColorCode());
        }

        //Make relationship between agreements and activity pattern
        activityPattern.setAgreements(agreements);
        activityPattern = activityPatternRepository.save(activityPattern);

        return activityPattern;
    }

    @Override
    public ActivityPattern retrieveActivityPatternByPatternCode(String patternCode) {
        return activityPatternRepository.findActivityPatternByPatternCode(patternCode);
    }
}