package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ActivityDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.VitalSignDTO;
import com.yoviro.rest.models.entity.VitalSign;
import com.yoviro.rest.models.repository.projections.SummaryVitalSignProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVitalSignService {
    Page<SummaryVitalSignProjection> findAll(Pageable pageable);

    Page<SummaryVitalSignProjection> summaryList(Pageable pageable, String firstName);

    VitalSign createOrUpdate(OfficialIdDTO officialIdDTO,
                             VitalSignDTO vitalSignDTO,
                             ActivityDTO activityDTO) throws Exception;
}