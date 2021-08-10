package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.ActivityPattern;
import com.yoviro.rest.models.repository.projections.SummaryActivityPatternProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.Subject;
import java.util.List;

@Repository
public interface ActivityPatternRepository extends PagingAndSortingRepository<ActivityPattern, Long> {
    @Transactional
    void deleteActivityPatternByPatternCode(String patternCode);

    @Transactional
    @Modifying
    @Query("delete from ActivityPattern as ap where ap.patternCode in (:patternCodes)")
    void deleteByPatternCodes(List<String> patternCodes);

    Page<SummaryActivityPatternProjection> findAllByIdNotNull(Pageable pageable); //Bring All

    Page<SummaryActivityPatternProjection> findAllBySubjectLike(Pageable pageable, String subject); //Bring All
}