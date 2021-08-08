package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ActivityPatternRepository extends PagingAndSortingRepository<ActivityPattern, Long> {
    Page<SummaryActivityPatternProjection> findAllByIdNotNull(Pageable pageable); //Bring All

    @Transactional
    void deleteActivityPatternByPatternCode(String patternCode);
}