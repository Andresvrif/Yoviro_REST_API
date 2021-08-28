package com.yoviro.rest.service;

import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.dto.AgreementDTO;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.repository.ActivityPatternRepository;
import com.yoviro.rest.models.repository.AgreementRepository;
import com.yoviro.rest.models.repository.projections.AgreementResidentProjection;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Iterable<ActivityPattern> findAllByEnable(Boolean enable) {
        return activityPatternRepository.enableActivityPatterns(enable);
    }

    @Override
    public ActivityPattern saveActivityPatternWithAgreements(ActivityPatternDTO activityPatternDTO,
                                                             Set<AgreementDTO> agreementDTOS) {
        ActivityPattern activityPattern = activityPatternRepository.save(modelMapper.map(activityPatternDTO, ActivityPattern.class));
        Set<Agreement> agreements = new HashSet<>();
        for (AgreementDTO agreementDTO : agreementDTOS) {
            agreements.add(modelMapper.map(agreementDTO, Agreement.class));
        }
        activityPattern.setAgreements(agreements);
        activityPatternRepository.save(activityPattern);

        return activityPattern;
    }
}