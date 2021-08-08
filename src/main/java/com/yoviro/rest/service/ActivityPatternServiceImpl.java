package com.yoviro.rest.service;

import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.repository.ActivityPatternRepository;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import com.yoviro.rest.service.interfaces.IActivityPatternService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActivityPatternServiceImpl implements IActivityPatternService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ActivityPatternRepository activityPatternRepository;

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
        //Long result = activityPatternRepository.deleteActivityPatternByPatternCode(patternCode);
        //System.out.println("result -------------> " + result);
    }

    @Override
    public Page<SummaryActivityPatternProjection> summaryList(Pageable pageable) {
        return activityPatternRepository.findAllByIdNotNull(pageable);
    }
}