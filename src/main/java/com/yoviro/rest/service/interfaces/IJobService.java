package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.search.SearchJobDTO;
import com.yoviro.rest.models.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IJobService {
    Page<Job> search(Pageable pageable, SearchJobDTO searchJobDTO);
}
