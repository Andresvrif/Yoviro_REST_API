package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Agreement;
import com.yoviro.rest.models.entity.Job;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface JobAgreementRepository extends PagingAndSortingRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    Set<Job> findAllByAgreementIn(Set<Agreement> agreements);
}