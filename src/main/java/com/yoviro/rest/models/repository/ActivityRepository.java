package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.ActivityPattern;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long> {
}