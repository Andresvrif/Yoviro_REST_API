package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ActivityPatternDTO;
import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.repository.projections.AgreementResidentProjection;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActivityPatternService {
    public ActivityPattern edit(ActivityPatternDTO activityPatternDTO);
    public ActivityPattern save(ActivityPatternDTO activityPatternDTO);
    void deleteActivityPatternByPatternCode(String patternCode);
    void deleteAllByPatternCodes(String[] patternCodesToDelete);
    public Page<SummaryActivityPatternProjection> summaryList(Pageable pageable);
    public Page<SummaryActivityPatternProjection> summaryList(Pageable pageable, String subject);

    /**
     * Author : Andrés V.
     * Desc : Returns the agreements and residents, related to the activity pattern
     * @param pageable
     * @param patternCode
     * @return
     */
    public Page<AgreementResidentProjection> agreementsResidentRelated(Pageable pageable, String patternCode);
}