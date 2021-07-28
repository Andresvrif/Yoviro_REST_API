package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.ActivityPattern;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActivityPatternRepository extends PagingAndSortingRepository<ActivityPattern, Long> {
    
}
