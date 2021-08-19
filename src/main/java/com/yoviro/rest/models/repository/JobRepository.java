package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.models.entity.Job;
import com.yoviro.rest.models.entity.Resident;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, Long>,
        JpaSpecificationExecutor<Job> {
}
