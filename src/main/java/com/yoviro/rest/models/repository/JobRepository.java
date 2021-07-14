package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.models.entity.Job;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobRepository extends PagingAndSortingRepository<Job, Long> {
}
