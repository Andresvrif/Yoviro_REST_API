package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.VitalSign;
import com.yoviro.rest.models.repository.projections.SummaryVitalSignProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VitalSignRepository extends PagingAndSortingRepository<VitalSign, Long> {
    Page<SummaryVitalSignProjection> summaryListAll(Pageable pageable); //Bring all

    Page<SummaryVitalSignProjection> summaryListLikeFirstName(Pageable pageable, String firstName);
}