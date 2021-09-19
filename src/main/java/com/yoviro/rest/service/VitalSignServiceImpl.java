package com.yoviro.rest.service;

import com.yoviro.rest.models.repository.VitalSignRepository;
import com.yoviro.rest.models.repository.projections.SummaryVitalSignProjection;
import com.yoviro.rest.service.interfaces.IVitalSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VitalSignServiceImpl implements IVitalSignService {
    @Autowired
    VitalSignRepository vitalSignRepository;

    @Override
    public Page<SummaryVitalSignProjection> findAll(Pageable pageable) {
        return vitalSignRepository.summaryListAll(pageable);
    }

    @Override
    public Page<SummaryVitalSignProjection> summaryList(Pageable pageable, String firstName) {
        firstName = "%".concat(firstName).concat("%");
        return vitalSignRepository.summaryListLikeFirstName(pageable, firstName);
    }
}
